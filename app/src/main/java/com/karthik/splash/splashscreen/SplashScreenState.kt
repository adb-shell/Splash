package com.karthik.splash.splashscreen

sealed class SplashScreenState {
    object SplashScreen : SplashScreenState()
    object FreshDashBoardScreen : SplashScreenState()
    object CachedDashBoardScreen : SplashScreenState()
    object NoInternetScreen : SplashScreenState()
}