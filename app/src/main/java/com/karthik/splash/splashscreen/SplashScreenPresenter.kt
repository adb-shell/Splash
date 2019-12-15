package com.karthik.splash.splashscreen

import com.karthik.splash.Storage.Cache
import com.karthik.splash.misc.Utils

class SplashScreenPresenter(private val splashview:SplashScreenContract.SplashView,
                            private val cache: Cache):SplashScreenContract.SplashPresenter {
    override fun decideScreens() {
        if (!Utils.isInternetAvailable(splashview.getSplashScreenContext()) && !cache.isCacheAvail) {
            splashview.showNoInternetScreen()
            return
        }
        splashview.showDashBoardScreen(shouldShowCache())
        splashview.closeScreen()
    }

    override fun shouldShowCache(): Boolean =
            !Utils.isInternetAvailable(splashview.getSplashScreenContext()) && cache.isCacheAvail
}