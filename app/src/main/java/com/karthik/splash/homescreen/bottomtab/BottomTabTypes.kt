package com.karthik.splash.homescreen.bottomtab

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BottomTabTypes(val title:String):Parcelable {
    data class New(val newtitle:String): BottomTabTypes(newtitle)
    data class Trending(val trendingtitle:String): BottomTabTypes(trendingtitle)
    data class Featured(val featuredtitle:String): BottomTabTypes(featuredtitle)
}