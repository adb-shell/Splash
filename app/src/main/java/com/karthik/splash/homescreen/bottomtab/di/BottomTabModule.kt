package com.karthik.splash.homescreen.bottomtab.di

import com.karthik.network.ServiceProvider
import com.karthik.network.home.bottomtab.repository.BottomTabRepository
import com.karthik.splash.homescreen.bottomtab.BottomTabTypes
import com.karthik.splash.homescreen.bottomtab.BottomTabViewModelFactory
import com.karthik.splash.misc.InternetHandler
import dagger.Module
import dagger.Provides

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
            internetHandler: InternetHandler,
            serviceProvider: ServiceProvider
    ): BottomTabRepository = BottomTabRepository(internetHandler,serviceProvider)

    @Provides
    fun providesBottomTabVFactory(bottomTabRepository: BottomTabRepository) =
            BottomTabViewModelFactory(bottomTabRepository, bottomtabtype)
}
