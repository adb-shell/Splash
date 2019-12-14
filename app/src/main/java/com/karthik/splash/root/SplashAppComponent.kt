package com.karthik.splash.root


import com.karthik.splash.homescreen.HomeScreenComponent
import com.karthik.splash.homescreen.HomeScreenModule
import com.karthik.splash.homescreen.bottomhometab.BottomHomeTabComponent
import com.karthik.splash.homescreen.bottomhometab.BottomHomeTabModule
import com.karthik.splash.homescreen.bottomliketab.BottomLikeTabComponent
import com.karthik.splash.homescreen.bottomliketab.BottomLikeTabModule
import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsTabComponent
import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsTabModule
import com.karthik.splash.homescreen.bottomtab.BottomTabComponent
import com.karthik.splash.homescreen.bottomtab.BottomTabModule
import com.karthik.splash.photodetailscreen.PhotoDetailScreenComponent
import com.karthik.splash.photodetailscreen.PhotoDetailScreenModule
import com.karthik.splash.splashscreen.SplashScreenComponent
import com.karthik.splash.splashscreen.SplashScreenModule

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SplashAppModule::class,SplashNetworkModule::class])
interface SplashAppComponent {
    fun inject(app:SplashApp)
    fun plus(splashScreenModule: SplashScreenModule): SplashScreenComponent
    fun plus(homeScreenModule: HomeScreenModule): HomeScreenComponent
    fun plus(bottomHomeTabModule: BottomHomeTabModule): BottomHomeTabComponent
    fun plus(homeTabFeedsModule: BottomTabModule): BottomTabComponent
    fun plus(likeTabFeedsModule: BottomLikeTabModule): BottomLikeTabComponent
    fun plus(settingsTabFeedsModule: BottomSettingsTabModule): BottomSettingsTabComponent
    fun plus(photoDetailModule: PhotoDetailScreenModule): PhotoDetailScreenComponent
}