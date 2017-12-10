package com.karthik.splash.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthik.splash.Models.Library.Library;
import com.karthik.splash.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikrk on 03/12/17.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryHolder>{
    private ArrayList<Library> libraries;

    public LibraryAdapter(ArrayList<Library> libraries){
        this.libraries = libraries;
    }

    @Override
    public LibraryAdapter.LibraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_row,
                parent,false);
        return new LibraryHolder(row);
    }

    @Override
    public void onBindViewHolder(LibraryAdapter.LibraryHolder holder, int position) {
        Context context = holder.libTitle.getContext();
        Library library = libraries.get(position);
        holder.libTitle.setText(library.libName);
        holder.libVersion.setText(context.getString(R.string.Version)+" "+library.version);
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    class LibraryHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.libtitle)
        TextView libTitle;
        @BindView(R.id.libversion)
        TextView libVersion;
        public LibraryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
