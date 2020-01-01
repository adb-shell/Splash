package com.karthik.splash.homescreen.di


import com.karthik.splash.homescreen.network.HomeScreenOAuthNetwork
import com.karthik.splash.homescreen.HomeScreenViewModel
import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class HomeScreenModule{

    @Provides
    fun providesOAuthNetworkLayer(okHttpClient: OkHttpClient)
            = HomeScreenOAuthNetwork(okHttpClient)

    @Provides
    fun providesHomeScreenPresenter(cache: Cache, oAuthOAuthNetwork: HomeScreenOAuthNetwork)
            = HomeScreenViewModel(cache, oAuthOAuthNetwork)
}