package com.karthik.splash.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.karthik.splash.Models.Photos;
import com.karthik.splash.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikrk on 19/11/17.
 */

public class FeedsRecyclerAdapter extends RecyclerView.Adapter<FeedsRecyclerAdapter.FeedsViewHolder> {
    private List<Photos> photosList;


    public FeedsRecyclerAdapter(List<Photos> photosList){
        this.photosList = photosList;
    }

    @Override
    public FeedsRecyclerAdapter.FeedsViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View row  = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_row,parent,false);
        return new FeedsViewHolder(row);
    }

    @Override
    public void onBindViewHolder(FeedsRecyclerAdapter.FeedsViewHolder holder,
                                 int position) {
        Context context = holder.itemView.getContext();

        if(position%2==0)
            holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(context,R.color.colorPrimary));
        else
            holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(context,R.color.colorPrimaryDark));

        Picasso.with(context)
                .load(photosList.get(position).urls.regular)
                .into(holder.feedsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        MaterialImageLoading.animate(holder.feedsImage)
                                .setDuration(2000)
                                .start();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    class FeedsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.feedImage)
        ImageView feedsImage;

        public FeedsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
