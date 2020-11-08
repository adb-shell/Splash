package com.karthik.splash.homescreen.bottomtab

import android.os.Parcelable
import com.karthik.splash.homescreen.bottomtab.network.BottomTabRepository
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BottomTabTypes(val title: String) : Parcelable {
    data class New(val newtitle: String) : BottomTabTypes(newtitle)
    data class Trending(val trendingtitle: String) : BottomTabTypes(trendingtitle)
    data class Featured(val featuredtitle: String) : BottomTabTypes(featuredtitle)
    companion object {
        fun convertTabToType(tabtype: BottomTabTypes): String {
            return when (tabtype) {
                is New -> BottomTabRepository.SORT_BY_LATEST
                is Featured -> BottomTabRepository.SORT_BY_POPULAR
                is Trending -> BottomTabRepository.SORT_BY_OLDEST
                else        -> ""
            }
        }
    }
}