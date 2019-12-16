package com.karthik.splash.homescreen


import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class HomeScreenModule(private val homescreenview: HomeScreenContract.View) {

    @Provides
    fun providesHomeScreenView() = homescreenview

    @Provides
    fun providesOAuthNetworkLayer(okHttpClient: OkHttpClient)
            = HomeScreenNetworkLayer(okHttpClient)

    @Provides
    fun providesHomeScreenPresenter(cache: Cache,oAuthNetworkLayer: HomeScreenNetworkLayer)
            : HomeScreenContract.Presenter = HomeScreenPresenter(homescreenview,cache,oAuthNetworkLayer)
}