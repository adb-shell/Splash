package com.karthik.splash.misc

import android.os.Bundle
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomliketab.models.Urls
import com.karthik.network.home.bottomliketab.models.User


private interface PhotoExtension {
    companion object {
        const val ID = "id"
        const val CREATED_TIME = "createdTime"
        const val HEIGHT = "height"
        const val WIDTH = "width"
        const val COLOR = "color"
        const val NUMBER_LIKES = "numberLikes"
        const val USER = "user"
        const val URLS = "urls"
    }
}


fun Photos.toBundle(): Bundle {
    val bundle = Bundle()
    bundle.putString(PhotoExtension.ID,this.id)
    bundle.putString(PhotoExtension.CREATED_TIME,this.createdTime)
    bundle.putString(PhotoExtension.HEIGHT,this.height)
    bundle.putString(PhotoExtension.WIDTH,this.width)
    bundle.putString(PhotoExtension.COLOR,this.color)
    bundle.putString(PhotoExtension.NUMBER_LIKES,this.numberLikes)
    bundle.putSerializable(PhotoExtension.USER,this.user)
    bundle.putSerializable(PhotoExtension.URLS,this.urls)
    return bundle
}


fun Bundle.toPhotos(): Photos {
    return Photos(
        id = this.getString(PhotoExtension.ID,""),
        createdTime = this.getString(PhotoExtension.CREATED_TIME,""),
        height = this.getString(PhotoExtension.HEIGHT,""),
        width = this.getString(PhotoExtension.WIDTH,""),
        color = this.getString(PhotoExtension.COLOR,""),
        numberLikes = this.getString(PhotoExtension.NUMBER_LIKES,""),
        user = this.getSerializable(PhotoExtension.USER) as User?,
        urls = this.getSerializable(PhotoExtension.URLS) as Urls?
    )
}