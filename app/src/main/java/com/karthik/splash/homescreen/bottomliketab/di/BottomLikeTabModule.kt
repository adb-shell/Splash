package com.karthik.splash.homescreen.bottomliketab.di

import com.karthik.splash.homescreen.bottomliketab.network.BottomLikeTabRepository
import com.karthik.splash.homescreen.bottomliketab.BottomLikeViewModelFactory
import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BottomLikeTabModule{

    @Provides
    fun providesUserServiceNetwork(retrofit: Retrofit,cache: Cache) =
            BottomLikeTabRepository(retrofit, cache)

    @Provides
    fun providesBottomLikeViewModelFactory(cache: Cache,
                                    userServiceRepository: BottomLikeTabRepository)
            : BottomLikeViewModelFactory = BottomLikeViewModelFactory(cache,userServiceRepository)
}