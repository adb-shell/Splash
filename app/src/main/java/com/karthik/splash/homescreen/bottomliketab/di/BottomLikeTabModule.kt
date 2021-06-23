package com.karthik.splash.homescreen.bottomliketab.di

import com.karthik.network.home.bottomliketab.IBottomLikeTabRepository
import com.karthik.network.home.bottomliketab.repository.BottomLikeTabRepository
import com.karthik.splash.homescreen.bottomliketab.BottomLikeViewModelFactory
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BottomLikeTabModule {

    @Provides
    fun providesUserServiceNetwork(
            memoryCache: MemoryCache,internetHandler: InternetHandler
    ): IBottomLikeTabRepository {
        return BottomLikeTabRepository(
            internetHandler = internetHandler,
            memoryCache = memoryCache
        )
    }


    @Provides
    fun providesBottomLikeViewModelFactory(
            memoryCache: MemoryCache,
            userServiceRepository: IBottomLikeTabRepository
    )
            : BottomLikeViewModelFactory =
            BottomLikeViewModelFactory(memoryCache, userServiceRepository)
}