package com.karthik.splash.homescreen.di



import com.karthik.splash.homescreen.network.HomeScreenOAuthRepository
import com.karthik.splash.homescreen.HomeScreenViewModelFactory
import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class HomeScreenModule{

    @Provides
    fun providesOAuthNetworkLayer(okHttpClient: OkHttpClient)
            = HomeScreenOAuthRepository(okHttpClient)

    @Provides
    fun providesHomeScreenPresenter(cache: Cache, oAuthOAuthRepository: HomeScreenOAuthRepository)
            = HomeScreenViewModelFactory(cache,oAuthOAuthRepository)
}