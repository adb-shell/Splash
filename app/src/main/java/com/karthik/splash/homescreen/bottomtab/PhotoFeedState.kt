package com.karthik.splash.homescreen.bottomtab

import com.karthik.splash.models.PhotosLists.Photos

sealed class PhotoFeedState {
    data class FeedState(val photos:ArrayList<Photos>):PhotoFeedState()
    data class FeedError(val error:Throwable):PhotoFeedState()
    object FeedNoInternet:PhotoFeedState()
}