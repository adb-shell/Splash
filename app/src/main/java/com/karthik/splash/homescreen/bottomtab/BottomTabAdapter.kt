package com.karthik.splash.homescreen.bottomtab

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.splash.R
import com.karthik.splash.misc.Utils
import com.karthik.splash.misc.loadImage
import com.karthik.splash.misc.toBundle
import com.karthik.splash.photodetailscreen.PhotoDetailScreen

@Deprecated("Use BottomFeedAdapter")
class BottomTabAdapter(private val photolist: ArrayList<Photos>) :
    RecyclerView.Adapter<BottomTabAdapter.BottomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int):
            BottomTabAdapter.BottomViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.feeds_row,
                parent, false)
        return BottomViewHolder(row)
    }

    override fun getItemCount(): Int =
            photolist.size

    override fun onBindViewHolder(holder: BottomTabAdapter.BottomViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.setAppropriateBackground(position, context)
        photolist[position].urls?.regular?.let { url ->
            holder.feedimage.loadImage(url, Utils.imageLoadDuration)
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
            intent.putExtra(Utils.photo, photolist[adapterPosition].toBundle())
            context?.startActivity(intent)
        }
    }
}