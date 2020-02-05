package com.karthik.splash.root

import android.app.Application
import androidx.room.Room
import com.karthik.splash.storage.db.SplashDatabase

class SplashApp : Application() {
    private lateinit var splashAppComponent: SplashAppComponent

    override fun onCreate() {
        super.onCreate()
        val splashDatabase = Room.databaseBuilder(this,SplashDatabase::class.java,"splashdb").build()
        splashAppComponent = DaggerSplashAppComponent.builder()
                .splashAppModule(SplashAppModule(this,splashDatabase))
                .build()
        splashAppComponent.inject(this)
    }

    fun getComponent() = splashAppComponent
}