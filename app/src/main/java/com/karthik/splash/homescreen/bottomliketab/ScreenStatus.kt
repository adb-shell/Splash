package com.karthik.splash.homescreen.bottomliketab

import com.karthik.network.home.bottomliketab.models.Photos

sealed class ScreenStatus {
    object ShowProgress: ScreenStatus()
    data class UserLikedPhotos(val likedPhotos: List<Photos>): ScreenStatus()
    data class ErrorFetchingPhotos(val error: Throwable) : ScreenStatus()
}