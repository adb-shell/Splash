package com.karthik.splash.homescreen.di


import com.karthik.network.home.IHomeScreenOAuthRepository
import com.karthik.splash.homescreen.HomeScreenViewModelFactory
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides

@Module
class HomeScreenModule {
    @Provides
    fun providesHomeScreenPresenter(
            memoryCache: MemoryCache,
            oAuthOAuthRepository: IHomeScreenOAuthRepository
    ) =
            HomeScreenViewModelFactory(
                    memoryCache = memoryCache,
                    homeScreenOAuthRepository = oAuthOAuthRepository
            )
}
