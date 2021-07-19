package com.karthik.splash.models

import com.karthik.network.home.bottomliketab.models.Photos

sealed class ScreenStatus {
    data class ScreenLoggedIn(val username: String) : ScreenStatus()
    object ScreenNotLoggedIn : ScreenStatus()
    object ShowProgress: ScreenStatus()
    data class UserLikedPhotos(val likedPhotos: List<Photos>): ScreenStatus()
    data class ErrorFetchingPhotos(val error: Throwable) : ScreenStatus()
}