package com.karthik.splash.splashscreen.di



import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.splashscreen.SplashScreen
import com.karthik.splash.splashscreen.SplashScreenViewModel
import com.karthik.splash.splashscreen.SplashViewContract
import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides

@Module
class SplashScreenModule(private val splashView: SplashViewContract) {

    @Provides
    fun providesSplashView() = splashView

    @Provides
    fun providesSplashScreenViewModel(cache: Cache):SplashScreenViewModel.SplashScreenViewModelFactory =
            SplashScreenViewModel.SplashScreenViewModelFactory(cache,splashView)
}