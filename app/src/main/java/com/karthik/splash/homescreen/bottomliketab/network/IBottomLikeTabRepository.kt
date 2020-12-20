package com.karthik.splash.homescreen.bottomliketab.network

import com.karthik.splash.homescreen.bottomliketab.UserLikedPhotoResponse

interface IBottomLikeTabRepository {
    suspend fun getUserLikedPhotos(): UserLikedPhotoResponse
}
