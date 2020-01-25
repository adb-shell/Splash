package com.karthik.splash.splashscreen.di

import com.karthik.splash.splashscreen.SplashScreen
import dagger.Subcomponent

@SplashScreenScope
@Subcomponent(modules = [SplashScreenModule::class])
interface SplashScreenComponent {
    fun inject(splashscreen: SplashScreen)
}