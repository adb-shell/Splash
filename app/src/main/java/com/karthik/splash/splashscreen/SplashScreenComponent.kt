package com.karthik.splash.splashscreen

import dagger.Subcomponent

@SplashScreenScope
@Subcomponent(modules = [SplashScreenModule::class])
interface SplashScreenComponent {
    fun inject(splashscreen:SplashScreen)
}