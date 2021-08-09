package com.karthik.splash.homescreen.bottomhometab.tab.di

import com.karthik.network.ServiceProvider
import com.karthik.network.home.bottomtab.repository.BottomTabRepository
import com.karthik.splash.homescreen.bottomhometab.tab.TabViewModelFactory
import com.karthik.splash.misc.InternetHandler
import dagger.Module
import dagger.Provides

@Module
class TabModule {
    private val isCacheAvailable: Boolean

    constructor(isCacheAvailable: Boolean?) {
        this.isCacheAvailable = isCacheAvailable != null
    }

    @Provides
    fun providesNetworkLayer(
            internetHandler: InternetHandler,
            serviceProvider: ServiceProvider
    ): BottomTabRepository = BottomTabRepository(internetHandler,serviceProvider)

    @Provides
    fun providesBottomTabVFactory(bottomTabRepository: BottomTabRepository) =
            TabViewModelFactory(bottomTabRepository)
}
