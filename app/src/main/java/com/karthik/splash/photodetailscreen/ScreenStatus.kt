package com.karthik.splash.photodetailscreen

import com.karthik.network.photodetailscreen.models.PhotoDetailInfo

sealed class ScreenStatus {
    object ShowProgress : ScreenStatus()
    object ErrorFetchingPhotoDetail : ScreenStatus()
    object ErrorLikingPhoto : ScreenStatus()
    data class PhotoDetailScreen(val photoInfo: PhotoDetailInfo) : ScreenStatus()
    object PhotoLikeSuccess : ScreenStatus()
}
