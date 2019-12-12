package com.karthik.splash.splashscreen

import com.karthik.splash.Storage.Cache
import dagger.Module
import dagger.Provides

@Module
class SplashScreenModule(private val splashView: SplashScreenContract.SplashView) {

    @Provides
    fun providesSplashView() = splashView

    @Provides
    fun providesSplashScreenPresenter(cache: Cache):SplashScreenContract.SplashPresenter = SplashScreenPresenter(splashView,cache)
}