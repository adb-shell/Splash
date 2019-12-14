package com.karthik.splash.homescreen.bottomsettingstab

import com.karthik.splash.Storage.Cache

class BottomSettingsTabPresenter(private val view: BottomSettingsTabContract.view,
                                 private val cache: Cache):BottomSettingsTabContract.presenter {
    override fun decideScreen() {
        if(cache.isUserLoggedIn){
            view.showLoggedInView(cache.userName)
            return
        }
        view.showNonLoggedInView()
    }

    override fun logOutUser() {
        cache.logOutUser()
        decideScreen()
    }

    override fun showDownloadedImages() {
        if(view.isReadPermissionGranted()){
            view.openFolder()
            return
        }
        view.askReadPermission()
    }
}