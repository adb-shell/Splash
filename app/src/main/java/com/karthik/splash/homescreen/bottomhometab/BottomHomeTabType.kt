package com.karthik.splash.homescreen.bottomhometab

import android.os.Parcelable
import com.karthik.network.home.bottomtab.repository.BottomTabRepository
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BottomHomeTabType(val title: String): Parcelable {
    class New(title: String) : BottomHomeTabType(title = title)
    class Trending(title: String) : BottomHomeTabType(title = title)
    class Featured(title: String) : BottomHomeTabType(title = title)

    override fun equals(other: Any?): Boolean {
        if(other is BottomHomeTabType)
            return other.title == this.title
        return false
    }

    override fun hashCode(): Int  = title.hashCode()
}

fun BottomHomeTabType.getTheTabType(): String {
    return when (this) {
        is BottomHomeTabType.New -> BottomTabRepository.SORT_BY_LATEST
        is BottomHomeTabType.Featured -> BottomTabRepository.SORT_BY_POPULAR
        is BottomHomeTabType.Trending -> BottomTabRepository.SORT_BY_OLDEST
        else -> ""
    }
}
