package com.karthik.splash.homescreen.bottomtab



import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.materialimageloading.MaterialImageLoading
import com.karthik.splash.R
import com.karthik.splash.misc.Utils
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.photodetailscreen.PhotoDetailScreen
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class BottomFeedAdapter : PagedListAdapter<Photos, RecyclerView.ViewHolder>(DIFF_CALLBACK){

    private var isloading:Boolean=false
    private val PROGRESS_VIEW = 0
    private val NORMAL_VIEW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val row:View
        if(viewType==NORMAL_VIEW){
            row = LayoutInflater.from(parent.context).inflate(R.layout.feeds_row,
                    parent, false)
            return BottomViewHolder(row)
        }
        row = LayoutInflater.from(parent.context).inflate(R.layout.progress_row,
                parent, false)
        return ProgressViewHolder(row)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BottomViewHolder){
            val context = holder.itemView.context
            setAppropriateBackground(position, holder, context)
            loadImage(position, holder, context)
        }
    }

    fun showPaginationProgress(){
        isloading = true
    }

    fun hidePaginationProgress(){
        isloading = false
    }

    override fun getItemViewType(position: Int): Int {
        if(isloading && position==itemCount-1){
            return PROGRESS_VIEW
        }
        return NORMAL_VIEW
    }

    companion object{
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Photos> = object : DiffUtil.ItemCallback<Photos>() {
            override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean {
                return oldItem == newItem
            }
        }
    }

    private fun loadImage(position:Int, holder: BottomViewHolder, context: Context){
        Picasso.with(context)
                .load(getItem(position)?.urls?.regular)
                .into(holder.feedimage, object : Callback {
                    override fun onSuccess() {
                        MaterialImageLoading.animate(holder.feedimage)
                                .setDuration(1200)
                                .start()
                    }
                    override fun onError() {}
                })
    }

    private fun setAppropriateBackground(position: Int, holder: BottomViewHolder,
                                         context: Context) {
        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.primary_light))
        else
            holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.divider))
    }

    inner class BottomViewHolder: RecyclerView.ViewHolder, View.OnClickListener {
        val feedimage: ImageView
        constructor(rootview: View):super(rootview){
            feedimage = rootview.findViewById(R.id.feedImage)
            rootview.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val context = view?.context
            val intent = Intent(context,PhotoDetailScreen::class.java)
            intent.putExtra(Utils.photo,getItem(adapterPosition))
            context?.startActivity(intent)
        }
    }

    class ProgressViewHolder(rootview:View): RecyclerView.ViewHolder(rootview)
}