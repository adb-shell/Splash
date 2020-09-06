package com.karthik.splash.homescreen.bottomtab

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.karthik.splash.R

fun RecyclerView.ViewHolder.setAppropriateBackground(position: Int,
                                                     context: Context) {
    if (position % 2 == 0) {
        this.itemView.setBackgroundColor(
                ContextCompat.getColor(context, R.color.primary_light))
        return
    }
    this.itemView.setBackgroundColor(
            ContextCompat.getColor(context, R.color.divider))
}