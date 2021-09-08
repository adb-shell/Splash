package com.karthik.splash.homescreen

import com.karthik.network.home.bottomliketab.models.Photos

sealed class HomeClickEvents {
    object LoginClick : HomeClickEvents()
    data class PhotoClick(val photos: Photos) : HomeClickEvents()
    object AboutClick : HomeClickEvents()
    object DownloadsClick : HomeClickEvents()
    object LogoutClick : HomeClickEvents()
    object NotLoggedIn : HomeClickEvents()
}
