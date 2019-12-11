package com.karthik.splash.root

import com.karthik.splash.Components.*
import com.karthik.splash.Modules.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SplashAppModule::class,SplashNetworkModule::class])
interface SplashAppComponent {
    fun inject(app:SplashApp)
    fun plus(splashScreenModule: SplashScreenModule):SplashScreenComponent
    fun plus(feedsScreenModule: FeedsScreenModule):FeedsScreenComponent
    fun plus(feedsHomeModule: FeedsHomeModule):FeedsHomeComponent
    fun plus(homeTabFeedsModule: HomeTabFeedsModule):HomeTabFeedsComponent
    fun plus(likeTabFeedsModule: LikeTabFeedsModule):LikeTabFeedsComponent
    fun plus(settingsTabFeedsModule: SettingsTabFeedsModule):SettingsTabFeedsComponent
    fun plus(photoDetailModule: PhotoDetailModule):PhotoDetailComponent
}