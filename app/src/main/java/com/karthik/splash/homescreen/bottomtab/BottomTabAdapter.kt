package com.karthik.splash.homescreen.bottomtab

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.florent37.materialimageloading.MaterialImageLoading
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.R
import com.karthik.splash.misc.Utils
import com.karthik.splash.photodetailscreen.PhotoDetailScreen
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class BottomTabAdapter(private val photolist:ArrayList<Photos>,
                       private val paginatedview:PaginatedView?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val PROGRESS_VIEW = 0
    private val NORMAL_VIEW = 1
    private var currentpage = 1
    private var ispageloading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): RecyclerView.ViewHolder {
        val row:View
        if(viewtype==NORMAL_VIEW){
            row = LayoutInflater.from(parent.context).inflate(R.layout.feeds_row,
                    parent, false)
            return BottomViewHolder(row)
        }
        row = LayoutInflater.from(parent.context).inflate(R.layout.progress_row,
                parent, false)
        return ProgressViewHolder(row)
    }

    override fun getItemCount(): Int = photolist.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BottomViewHolder){
            val context = holder.itemView.context
            setAppropriateBackground(position, holder, context)
            loadImage(position, holder, context)
        }else{
            ispageloading = true
            paginatedview?.getPage(++currentpage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(paginatedview!=null && position==photolist.size-1 && currentpage<=paginatedview.getMaxPageLimit()){
            return PROGRESS_VIEW
        }
        return NORMAL_VIEW
    }

    fun addPaginatedItems(paginatedPhotos: List<Photos>) {
        val oldSize = photolist.size
        photolist.addAll(paginatedPhotos)
        ispageloading = false
        notifyItemRangeChanged(oldSize,paginatedPhotos.size)
    }

    inner class BottomViewHolder: RecyclerView.ViewHolder,View.OnClickListener {
        val feedimage:ImageView
        constructor(rootview:View):super(rootview){
            feedimage = rootview.findViewById(R.id.feedImage)
            rootview.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val context = view?.context
            val intent = Intent(context, PhotoDetailScreen::class.java)
            intent.putExtra(Utils.Photo, photolist[adapterPosition])
            context?.startActivity(intent)
        }
    }

    inner class ProgressViewHolder(rootview:View):RecyclerView.ViewHolder(rootview)

    private fun loadImage(position:Int,holder:BottomViewHolder,context: Context){
        Picasso.with(context)
                .load(photolist[position].urls?.regular)
                .into(holder.feedimage, object : Callback {
                    override fun onSuccess() {
                        MaterialImageLoading.animate(holder.feedimage)
                                .setDuration(1200)
                                .start()
                    }
                    override fun onError() {}
                })
    }

    private fun setAppropriateBackground(position: Int,holder: BottomViewHolder,
                                         context: Context) {
        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.primary_light))
        else
            holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.divider))
    }
}