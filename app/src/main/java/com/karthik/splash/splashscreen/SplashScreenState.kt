package com.karthik.splash.splashscreen

sealed class SplashScreenState {
    object FreshDashBoardScreen : SplashScreenState()
    object CachedDashBoardScreen : SplashScreenState()
    object NoInternetScreen : SplashScreenState()
}