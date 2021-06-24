package com.karthik.splash.homescreen.bottomliketab.di

import com.karthik.network.ServiceProvider
import com.karthik.network.home.bottomliketab.IBottomLikeTabRepository
import com.karthik.network.home.bottomliketab.repository.BottomLikeTabRepository
import com.karthik.splash.homescreen.bottomliketab.BottomLikeViewModelFactory
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides

@Module
class BottomLikeTabModule {

    @Provides
    fun providesUserServiceNetwork(
            memoryCache: MemoryCache,
            internetHandler: InternetHandler,
            serviceProvider: ServiceProvider
    ): IBottomLikeTabRepository {
        return BottomLikeTabRepository(
            internetHandler = internetHandler,
            memoryCache = memoryCache,
            serviceProvider = serviceProvider
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