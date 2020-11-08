package com.karthik.splash.homescreen.di


import com.karthik.splash.homescreen.network.HomeScreenOAuthRepository
import com.karthik.splash.homescreen.HomeScreenViewModelFactory
import com.karthik.splash.homescreen.network.IHomeScreenOAuthRepository
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class HomeScreenModule {

    @Provides
    fun providesOAuthNetworkLayer(okHttpClient: OkHttpClient): IHomeScreenOAuthRepository =
            HomeScreenOAuthRepository(okHttpClient)

    @Provides
    fun providesHomeScreenPresenter(
            memoryCache: MemoryCache,
            oAuthOAuthRepository: IHomeScreenOAuthRepository
    ) =
            HomeScreenViewModelFactory(memoryCache, oAuthOAuthRepository)
}