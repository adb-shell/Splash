package com.karthik.splash.splashscreen.di

import com.karthik.splash.splashscreen.SplashScreenContract
import com.karthik.splash.splashscreen.SplashScreenPresenter
import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides

@Module
class SplashScreenModule(private val splashView: SplashScreenContract.SplashView) {

    @Provides
    fun providesSplashView() = splashView

    @Provides
    fun providesSplashScreenPresenter(cache: Cache): SplashScreenContract.SplashPresenter = SplashScreenPresenter(splashView, cache)
}