package com.karthik.splash.root


import com.karthik.network.di.NetworkProvider
import com.karthik.splash.homescreen.di.HomeScreenComponent
import com.karthik.splash.homescreen.di.HomeScreenModule
import com.karthik.splash.photodetailscreen.di.PhotoDetailScreenComponent
import com.karthik.splash.photodetailscreen.di.PhotoDetailScreenModule
import com.karthik.splash.splashscreen.di.SplashScreenComponent
import com.karthik.splash.splashscreen.di.SplashScreenModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SplashAppModule::class, NetworkModule::class, NetworkProvider::class])
interface SplashAppComponent {
    fun inject(app: SplashApp)
    fun plus(splashScreenModule: SplashScreenModule): SplashScreenComponent
    fun plus(homeScreenModule: HomeScreenModule): HomeScreenComponent
    fun plus(photoDetailModule: PhotoDetailScreenModule): PhotoDetailScreenComponent
}