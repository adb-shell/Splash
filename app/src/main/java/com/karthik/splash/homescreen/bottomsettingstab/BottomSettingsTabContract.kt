package com.karthik.splash.homescreen.bottomsettingstab

/**
 * Created by karthikrk on 03/12/17.
 */

interface BottomSettingsTabContract {
    interface view {
        fun isReadPermissionGranted(): Boolean
        fun showLoggedInView(name: String)
        fun showNonLoggedInView()
        fun openFolder()
        fun askReadPermission()
    }

    interface presenter {
        fun decideScreen()
        fun logOutUser()
        fun showDownloadedImages()
    }
}
