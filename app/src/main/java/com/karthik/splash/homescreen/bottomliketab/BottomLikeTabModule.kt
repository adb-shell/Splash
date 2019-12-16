package com.karthik.splash.homescreen.bottomliketab

import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BottomLikeTabModule(private val view: BottomLikeTabContract.view) {

    @Provides
    fun providesBottomLikeTabView() = view

    @Provides
    fun providesUserServiceNetwork(retrofit: Retrofit,cache: Cache) =
            BottomLikeTabNetworkLayer(retrofit, cache)

    @Provides
    fun providesBottomLikePresenter(cache: Cache,
                                    userServiceNetworkLayer: BottomLikeTabNetworkLayer)
            :BottomLikeTabContract.presenter = BottomLikeTabPresenter(view,cache,userServiceNetworkLayer)
}