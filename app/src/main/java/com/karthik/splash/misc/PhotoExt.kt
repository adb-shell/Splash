package com.karthik.splash.misc

import android.os.Bundle
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomliketab.models.Urls
import com.karthik.network.home.bottomliketab.models.User


private const val Id = "id"
private const val CreatedTime = "createdTime"
private const val Height = "height"
private const val Width = "width"
private const val Color = "color"
private const val NumberLikes = "numberLikes"
private const val User = "user"
private const val Urls = "urls"


fun Photos.toBundle(): Bundle {
    val bundle = Bundle()
    bundle.putString(Id,this.id)
    bundle.putString(CreatedTime,this.createdTime)
    bundle.putString(Height,this.height)
    bundle.putString(Width,this.width)
    bundle.putString(Color,this.color)
    bundle.putString(NumberLikes,this.numberLikes)
    bundle.putSerializable(User,this.user)
    bundle.putSerializable(Urls,this.urls)
    return bundle
}


fun Bundle.toPhotos(): Photos {
    return Photos(
        id = this.getString(Id,""),
        createdTime = this.getString(CreatedTime,""),
        height = this.getString(Height,""),
        width = this.getString(Width,""),
        color = this.getString(Color,""),
        numberLikes = this.getString(NumberLikes,""),
        user = this.getSerializable(User) as User?,
        urls = this.getSerializable(Urls) as Urls?
    )
}