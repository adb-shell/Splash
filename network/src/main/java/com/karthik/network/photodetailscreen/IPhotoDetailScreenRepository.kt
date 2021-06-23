package com.karthik.network.photodetailscreen

import com.karthik.network.photodetailscreen.models.PhotoDetailsResponse
import com.karthik.network.photodetailscreen.models.PhotoLikeResponse

interface IPhotoDetailScreenRepository {
    suspend fun getPhotoInfo(
            id: String
    ): PhotoDetailsResponse

    suspend fun likePhoto(
            id: String
    ): PhotoLikeResponse
}
