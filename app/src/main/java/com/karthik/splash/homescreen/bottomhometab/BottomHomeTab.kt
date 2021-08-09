package com.karthik.splash.homescreen.bottomhometab

import android.os.Parcelable
import com.karthik.network.home.bottomtab.repository.BottomTabRepository
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BottomHomeTab(val title: String): Parcelable {
    class New(title: String) : BottomHomeTab(title = title)
    class Trending(title: String) : BottomHomeTab(title = title)
    class Featured(title: String) : BottomHomeTab(title = title)

    override fun equals(other: Any?): Boolean {
        if(other is BottomHomeTab)
            return other.title == this.title
        return false
    }

    override fun hashCode(): Int  = title.hashCode()
}

fun BottomHomeTab.getTheTabType(): String {
    return when (this) {
        is BottomHomeTab.New -> BottomTabRepository.SORT_BY_LATEST
        is BottomHomeTab.Featured -> BottomTabRepository.SORT_BY_POPULAR
        is BottomHomeTab.Trending -> BottomTabRepository.SORT_BY_OLDEST
        else -> ""
    }
}
