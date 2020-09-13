package com.karthik.splash.misc

import android.widget.ImageView
import com.github.florent37.materialimageloading.MaterialImageLoading
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

fun ImageView.loadImage(url: String, duration: Int, transform: Transformation? = null) {
    transform?.let {
        Picasso.with(context)
                .load(url)
                .transform(transform)
                .into(this, object : Callback {
                    override fun onSuccess() {
                        MaterialImageLoading.animate(this@loadImage)
                                .setDuration(duration)
                                .start()
                    }

                    override fun onError() {}
                })
    } ?: Picasso.with(context)
            .load(url)
            .into(this, object : Callback {
                override fun onSuccess() {
                    MaterialImageLoading.animate(this@loadImage)
                            .setDuration(duration)
                            .start()
                }

                override fun onError() {}
            })
}