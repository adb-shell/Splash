package com.karthik.network.home.bottomtab.models

import com.karthik.network.home.bottomliketab.models.Photos


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

sealed class PhotoNetworkResponse {
    /**
     * Response type
     */
    data class FeedSuccessResponse(val photos: ArrayList<Photos>) : PhotoNetworkResponse()
    data class FeedFailureResponse(val error: Throwable) : PhotoNetworkResponse()
}
