package com.karthik.splash.photodetailscreen

import androidx.lifecycle.LiveData
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import com.karthik.splash.photodetailscreen.network.PhotoDetailsNetworkState
import com.karthik.splash.photodetailscreen.network.PhotoDetailsResponse
import com.karthik.splash.photodetailscreen.network.PhotoLikeResponse

interface IPhotoDetailScreenRepository {
    suspend fun getPhotoInfo(
            id: String
    ): PhotoDetailsResponse

    suspend fun likePhoto(
            id: String
    ): PhotoLikeResponse

    fun getPhotoDetailNetworkState(): LiveData<PhotoDetailsNetworkState>
}
