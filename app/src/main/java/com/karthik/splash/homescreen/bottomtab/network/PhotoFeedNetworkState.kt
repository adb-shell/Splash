package com.karthik.splash.homescreen.bottomtab.network


sealed class PhotoFeedNetworkState {
    object FeedNetworkLoadSuccess : PhotoFeedNetworkState()
    data class FeedNetworkError(val error: Throwable) : PhotoFeedNetworkState()
    object FeedNetworkNoInternet : PhotoFeedNetworkState()

    /**
     * Pagination
     */
    object FeedNetworkPaginationLoading : PhotoFeedNetworkState()
    object FeedNetworkPaginationLoadSuccess : PhotoFeedNetworkState()
    data class FeedNetworkPaginationLoadError(val error: Throwable) : PhotoFeedNetworkState()
}