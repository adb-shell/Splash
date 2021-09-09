package com.karthik.splash.homescreen.bottomliketab

import com.karthik.network.home.bottomliketab.models.Photos

sealed class LikeScreenNetworkStatus {
    object ShowProgress: LikeScreenNetworkStatus()
    data class UserLikedPhotos(val likedPhotos: List<Photos>): LikeScreenNetworkStatus()
    data class ErrorFetchingPhotos(val error: Throwable) : LikeScreenNetworkStatus()
}