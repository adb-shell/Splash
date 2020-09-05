package com.karthik.splash.splashscreen.di



import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.splashscreen.SplashScreenViewModelFactory
import com.karthik.splash.splashscreen.SplashViewContract
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides

@Module
class SplashScreenModule {

    @Provides
    fun providesSplashScreenViewModel(memoryCache: MemoryCache,internetHandler: InternetHandler): SplashScreenViewModelFactory =
            SplashScreenViewModelFactory(memoryCache,internetHandler)
}