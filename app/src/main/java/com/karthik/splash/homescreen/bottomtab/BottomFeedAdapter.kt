package com.karthik.splash.homescreen.bottomtab


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.splash.R
import com.karthik.splash.misc.Utils
import com.karthik.splash.misc.loadImage
import com.karthik.splash.misc.toBundle
import com.karthik.splash.photodetailscreen.PhotoDetailScreen

class BottomFeedAdapter : PagedListAdapter<Photos, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var isloading: Boolean = false
    private val progressView = 0
    private val normalView = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val row: View
        if (viewType == normalView) {
            row = LayoutInflater.from(parent.context).inflate(R.layout.feeds_row,
                    parent, false)
            return BottomViewHolder(row)
        }
        row = LayoutInflater.from(parent.context).inflate(R.layout.progress_row,
                parent, false)
        return ProgressViewHolder(row)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BottomViewHolder) {
            val context = holder.itemView.context
            holder.setAppropriateBackground(position, context)
            getItem(position)?.urls?.regular?.let { url ->
                holder.feedimage.loadImage(url, Utils.imageLoadDuration)
            }
        }
    }

    fun showPaginationProgress() {
        isloading = true
    }

    fun hidePaginationProgress() {
        isloading = false
    }

    override fun getItemViewType(position: Int): Int {
        if (isloading && position == itemCount - 1) {
            return progressView
        }
        return normalView
    }

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Photos> =
                object : DiffUtil.ItemCallback<Photos>() {
                    override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean =
                            oldItem.id === newItem.id

                    override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean =
                            oldItem == newItem
                }
    }

    inner class BottomViewHolder : RecyclerView.ViewHolder, View.OnClickListener {
        val feedimage: ImageView

        constructor(rootview: View) : super(rootview) {
            feedimage = rootview.findViewById(R.id.feedImage)
            rootview.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val context = view?.context
            val intent = Intent(context, PhotoDetailScreen::class.java)
            intent.putExtra(Utils.photo, getItem(adapterPosition)?.toBundle())
            context?.startActivity(intent)
        }
    }

    inner class ProgressViewHolder(rootview: View) : RecyclerView.ViewHolder(rootview)
}