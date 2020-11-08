package com.karthik.splash.homescreen.bottomtab.di

import com.karthik.splash.homescreen.bottomtab.BottomTabTypes
import com.karthik.splash.homescreen.bottomtab.network.BottomTabRepository
import com.karthik.splash.homescreen.bottomtab.BottomTabViewModelFactory
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
import com.karthik.splash.storage.db.SplashDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BottomTabModule {
    private val isCacheAvailable: Boolean
    private val bottomtabtype: BottomTabTypes

    constructor(isCacheAvailable: Boolean?, bottomtabtype: BottomTabTypes?) {
        requireNotNull(bottomtabtype) { "type cannot be null" }
        this.isCacheAvailable = isCacheAvailable != null
        this.bottomtabtype = bottomtabtype
    }

    @Provides
    fun providesNetworkLayer(
            retrofit: Retrofit,
            splashDao: SplashDao,
            cache: MemoryCache,
            internetHandler: InternetHandler
    ) =
            BottomTabRepository(retrofit, splashDao, cache, internetHandler)

    @Provides
    fun providesBottomTabVFactory(bottomTabRepository: BottomTabRepository) =
            BottomTabViewModelFactory(isCacheAvailable, bottomTabRepository, bottomtabtype)
}