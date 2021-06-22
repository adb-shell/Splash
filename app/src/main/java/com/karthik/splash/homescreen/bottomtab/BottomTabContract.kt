package com.karthik.splash.homescreen.bottomtab

import com.karthik.network.home.bottomliketab.models.Photos


/**
 * Created by karthikrk on 16/11/17.
 */

interface BottomTabContract {
    interface View {
        fun isFeedListVisible(): Boolean
        fun hideProgressBar()
        fun showPhotosList(photos: ArrayList<Photos>)
        fun showEmptyScreen()
        fun showNoInternetScreen()
    }

    interface Presenter {
        fun isPaginatedItems(): Boolean
        fun getPageMaxLimit(): Int
        fun getFeeds(mode: BottomTabTypes?, isFromCache: Boolean?, pageSize: Int)
        fun getPaginatedFeeds(mode: BottomTabTypes?, pageSize: Int)
        fun clearResources()
        fun manageErrors(e: Throwable)
        fun managePhotos(photos: ArrayList<Photos>)
    }
}
