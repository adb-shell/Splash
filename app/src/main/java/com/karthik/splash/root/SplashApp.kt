package com.karthik.splash.root

import android.app.Application

class SplashApp : Application() {
    private lateinit var splashAppComponent: SplashAppComponent

    override fun onCreate() {
        super.onCreate()
        splashAppComponent = DaggerSplashAppComponent.builder()
                .splashAppModule(SplashAppModule(this))
                .build()
        splashAppComponent.inject(this)
    }

    fun getComponent() = splashAppComponent
}