package com.karthik.splash.homescreen.bottomliketab

sealed class LikeFeedNetworkState {
    object FeedNetworkLoading : LikeFeedNetworkState()
    object FeedNetworkLoadSuccess : LikeFeedNetworkState()
    data class FeedNetworkError(val error: Throwable) : LikeFeedNetworkState()
}