package com.karthik.splash.photodetailscreen.network

sealed class PhotoDetailsNetworkState {
    object PhotoDetailsNetworkLoadSuccess : PhotoDetailsNetworkState()
    data class PhotoDetailsNetworkLoadError(val error: Throwable) : PhotoDetailsNetworkState()
    data class PhotoLikeNetworkLoadError(val error: Throwable) : PhotoDetailsNetworkState()
}