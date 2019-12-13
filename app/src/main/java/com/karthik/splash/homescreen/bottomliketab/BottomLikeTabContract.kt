package com.karthik.splash.homescreen.bottomliketab

import com.karthik.splash.Models.PhotosLists.Photos

/**
 * Created by karthikrk on 25/11/17.
 */

interface BottomLikeTabContract {
    interface view {
        fun showLoginScreen()
        fun showLikesList(likedPhotos: List<Photos>)
        fun showProgress()
        fun hideProgress()
        fun showEmptyLikedScreen()
        fun openLoginOauthUrl(oauthurl: String)
    }

    interface presenter {
        fun decideScreenType()
        fun login()
        fun clearResources()
    }
}
