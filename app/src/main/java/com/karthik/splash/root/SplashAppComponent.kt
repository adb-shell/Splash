package com.karthik.splash.root

import com.karthik.splash.Components.*
import com.karthik.splash.Modules.*
import com.karthik.splash.homescreen.HomeScreenComponent
import com.karthik.splash.homescreen.HomeScreenModule
import com.karthik.splash.homescreen.bottomhometab.BottomHomeTabComponent
import com.karthik.splash.homescreen.bottomhometab.BottomHomeTabModule
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
    fun plus(homeTabFeedsModule: HomeTabFeedsModule):HomeTabFeedsComponent
    fun plus(likeTabFeedsModule: LikeTabFeedsModule):LikeTabFeedsComponent
    fun plus(settingsTabFeedsModule: SettingsTabFeedsModule):SettingsTabFeedsComponent
    fun plus(photoDetailModule: PhotoDetailModule):PhotoDetailComponent
}