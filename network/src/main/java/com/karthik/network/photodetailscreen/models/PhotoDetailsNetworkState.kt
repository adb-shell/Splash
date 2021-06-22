package com.karthik.network.photodetailscreen.models

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
