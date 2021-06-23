package com.karthik.network.home.bottomliketab

import com.karthik.network.home.bottomliketab.models.UserLikedPhotoResponse


interface IBottomLikeTabRepository {
    suspend fun getUserLikedPhotos(): UserLikedPhotoResponse
}
