package com.karthik.splash.homescreen.bottomliketab.di

import com.karthik.splash.homescreen.bottomliketab.network.BottomLikeTabRepository
import com.karthik.splash.homescreen.bottomliketab.BottomLikeViewModelFactory
import com.karthik.splash.homescreen.bottomliketab.network.BottomLikeTabNetworkService
import com.karthik.splash.homescreen.bottomliketab.network.IBottomLikeTabRepository
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
import com.karthik.splash.storage.db.SplashDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BottomLikeTabModule {

    @Provides
    fun providesUserServiceNetwork(
            retrofit: Retrofit, memoryCache: MemoryCache, localdb: SplashDao,
            internetHandler: InternetHandler
    ): IBottomLikeTabRepository {
        val likeTabService = retrofit.create(BottomLikeTabNetworkService::class.java)
        return BottomLikeTabRepository(likeTabService, memoryCache, localdb, internetHandler)
    }


    @Provides
    fun providesBottomLikeViewModelFactory(
            memoryCache: MemoryCache,
            userServiceRepository: IBottomLikeTabRepository
    )
            : BottomLikeViewModelFactory =
            BottomLikeViewModelFactory(memoryCache, userServiceRepository)
}