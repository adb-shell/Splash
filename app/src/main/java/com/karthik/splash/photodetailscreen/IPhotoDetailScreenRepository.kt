package com.karthik.splash.photodetailscreen

import androidx.lifecycle.LiveData
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import com.karthik.splash.photodetailscreen.network.PhotoDetailsNetworkState

interface IPhotoDetailScreenRepository {
    fun getPhotoInfo(id: String,
                     successhander: (detail: PhotoDetailInfo) -> Unit,
                     errorhandler: (e: Throwable) -> Unit)
    fun likePhoto(id: String,
                  successhander: (likeResponse: LikeResponse) -> Unit,
                  errorhandler: (e: Throwable) -> Unit)
    fun getPhotoDetailNetworkState(): LiveData<PhotoDetailsNetworkState>
    fun clearResources()
}