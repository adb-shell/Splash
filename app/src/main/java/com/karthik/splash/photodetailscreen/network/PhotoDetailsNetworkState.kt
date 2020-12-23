package com.karthik.splash.photodetailscreen.network

import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo

sealed class PhotoDetailsNetworkState {
    object PhotoDetailsNetworkLoadSuccess : PhotoDetailsNetworkState()
    object PhotoDetailsNetworkLoadError : PhotoDetailsNetworkState()
    object PhotoLikeNetworkLoadError : PhotoDetailsNetworkState()
}

sealed class PhotoDetailsResponse {
    data class PhotoDetailsSuccessResponse(val photoDetail: PhotoDetailInfo) :
        PhotoDetailsResponse()

    data class PhotoDetailsFailureResponse(val error: Throwable) : PhotoDetailsResponse()
}


sealed class PhotoLikeResponse {
    data class PhotoDetailsSuccessResponse(val likeResponse: LikeResponse) :
        PhotoLikeResponse()

    data class PhotoLikeFailureResponse(val error: Throwable) : PhotoLikeResponse()
}
