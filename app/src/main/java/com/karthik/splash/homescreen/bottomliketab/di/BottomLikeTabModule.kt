package com.karthik.splash.homescreen.bottomliketab.di

import com.karthik.splash.homescreen.bottomliketab.network.BottomLikeTabRepository
import com.karthik.splash.homescreen.bottomliketab.BottomLikeViewModelFactory
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
import com.karthik.splash.storage.db.SplashDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BottomLikeTabModule{

    @Provides
    fun providesUserServiceNetwork(retrofit: Retrofit, memoryCache: MemoryCache,localdb:SplashDao,
                                   internetHandler: InternetHandler) =
            BottomLikeTabRepository(retrofit, memoryCache,localdb,internetHandler)

    @Provides
    fun providesBottomLikeViewModelFactory(memoryCache: MemoryCache,
                                           userServiceRepository: BottomLikeTabRepository)
            : BottomLikeViewModelFactory = BottomLikeViewModelFactory(memoryCache,userServiceRepository)
}