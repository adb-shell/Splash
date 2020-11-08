package com.karthik.splash.root


import com.karthik.splash.homescreen.di.HomeScreenComponent
import com.karthik.splash.homescreen.di.HomeScreenModule
import com.karthik.splash.homescreen.bottomliketab.di.BottomLikeTabComponent
import com.karthik.splash.homescreen.bottomliketab.di.BottomLikeTabModule
import com.karthik.splash.homescreen.bottomsettingstab.di.BottomSettingsTabComponent
import com.karthik.splash.homescreen.bottomsettingstab.di.BottomSettingsTabModule
import com.karthik.splash.homescreen.bottomtab.di.BottomTabComponent
import com.karthik.splash.homescreen.bottomtab.di.BottomTabModule
import com.karthik.splash.photodetailscreen.di.PhotoDetailScreenComponent
import com.karthik.splash.photodetailscreen.di.PhotoDetailScreenModule
import com.karthik.splash.splashscreen.di.SplashScreenComponent
import com.karthik.splash.splashscreen.di.SplashScreenModule

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SplashAppModule::class, SplashNetworkModule::class])
interface SplashAppComponent {
    fun inject(app: SplashApp)
    fun plus(splashScreenModule: SplashScreenModule): SplashScreenComponent
    fun plus(homeScreenModule: HomeScreenModule): HomeScreenComponent
    fun plus(homeTabFeedsModule: BottomTabModule): BottomTabComponent
    fun plus(likeTabFeedsModule: BottomLikeTabModule): BottomLikeTabComponent
    fun plus(settingsTabFeedsModule: BottomSettingsTabModule): BottomSettingsTabComponent
    fun plus(photoDetailModule: PhotoDetailScreenModule): PhotoDetailScreenComponent
}