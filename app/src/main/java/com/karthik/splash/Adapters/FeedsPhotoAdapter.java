package com.karthik.splash.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.karthik.splash.Models.PhotosLists.Photos;
import com.karthik.splash.R;
import com.karthik.splash.Views.PaginatedView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikrk on 19/11/17.
 */

public class FeedsPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Photos> photosList;
    private int currentPage = 1;
    private final int NORMAL_VIEW=0,PROGRESS_VIEW=1;
    private PaginatedView paginatedView;
    private boolean isPageLoading;


    public FeedsPhotoAdapter(List<Photos> photosList, PaginatedView view){
        this.photosList = photosList;
        this.paginatedView = view;
    }


    public void addPaginatedItems(List<Photos> paginatedPhotos){
        int oldSize = photosList.size();
        photosList.addAll(paginatedPhotos);
        isPageLoading = false;
        notifyItemRangeChanged(oldSize,paginatedPhotos.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View row;
        if(viewType==NORMAL_VIEW){
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_row,
                    parent,false);
            return new FeedsViewHolder(row);
        }
        row = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_row,
                parent,false);
        return new ProgressViewHolder(row);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                 int position) {
        if(holder instanceof FeedsPhotoAdapter.FeedsViewHolder)
            bindNormalView(holder, position);

        else if(isPageNotExceeded() && !isPageLoading)
            bindProgressView();
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(paginatedView!=null && position==photosList.size()-1 && isPageNotExceeded()){
            return PROGRESS_VIEW;
        }
        return NORMAL_VIEW;
    }


    private boolean isPageNotExceeded() {
        return currentPage<=paginatedView.getMaxPageLimit();
    }

    private void bindNormalView(RecyclerView.ViewHolder viewHolder, int position) {
        FeedsPhotoAdapter.FeedsViewHolder holder =
                (FeedsPhotoAdapter.FeedsViewHolder)viewHolder;

        Context context = holder.itemView.getContext();
        setAppropriateBackground(position, holder, context);
        loadImage(position, holder, context);
    }

    private void loadImage(int position, FeedsViewHolder holder, Context context) {
        Picasso.with(context)
                .load(photosList.get(position).urls.regular)
                .into(holder.feedsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        MaterialImageLoading.animate(holder.feedsImage)
                                .setDuration(1200)
                                .start();
                    }
                    @Override
                    public void onError() {}
                });
    }

    private void bindProgressView() {
        isPageLoading = true;
        paginatedView.getPage(++currentPage);
    }

    private void setAppropriateBackground(int position, FeedsViewHolder holder, Context context) {
        if(position%2==0)
            holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.primary_light));
        else
            holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(context,R.color.divider));
    }


    class FeedsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.feedImage)
        ImageView feedsImage;
        public FeedsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            //Todo:implement to take it to the detail
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder{
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
