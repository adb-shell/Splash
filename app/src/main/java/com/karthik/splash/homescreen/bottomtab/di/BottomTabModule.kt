package com.karthik.splash.homescreen.bottomtab.di

import com.karthik.network.home.bottomtab.repository.BottomTabRepository
import com.karthik.splash.homescreen.bottomtab.BottomTabTypes
import com.karthik.splash.homescreen.bottomtab.BottomTabViewModelFactory
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
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
            cache: MemoryCache,
            internetHandler: InternetHandler
    ): BottomTabRepository {
        return BottomTabRepository(retrofit, cache, internetHandler)
    }

    @Provides
    fun providesBottomTabVFactory(bottomTabRepository: BottomTabRepository) =
            BottomTabViewModelFactory(bottomTabRepository, bottomtabtype)
}
