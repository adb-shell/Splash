package com.karthik.splash.splashscreen

import android.content.Context

/**
 * Created by karthikrk on 12/11/17.
 */

interface SplashScreenContract {
    interface SplashView {
        fun getSplashScreenContext():Context
        fun showNoInternetScreen()
        fun showDashBoardScreen(shouldShowCache: Boolean)
        fun closeScreen()
    }

    interface SplashPresenter {
        fun decideScreens()
        fun shouldShowCache(): Boolean
    }
}
