package com.karthik.splash.homescreen.di


import com.karthik.network.ServiceProvider
import com.karthik.network.home.IHomeScreenOAuthRepository
import com.karthik.network.home.bottomliketab.IBottomLikeTabRepository
import com.karthik.network.home.bottomliketab.repository.BottomLikeTabRepository
import com.karthik.network.home.bottomtab.repository.BottomTabRepository
import com.karthik.splash.homescreen.HomeScreenViewModelFactory
import com.karthik.splash.homescreen.bottomhometab.tab.TabViewModelFactory
import com.karthik.splash.homescreen.bottomliketab.BottomLikeViewModelFactory
import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsViewModelFactory
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides

@Module
class HomeScreenModule {
    @Provides
    fun providesHomeScreenPresenter(
        memoryCache: MemoryCache,
        oAuthOAuthRepository: IHomeScreenOAuthRepository
    ) =
        HomeScreenViewModelFactory(
            memoryCache = memoryCache,
            homeScreenOAuthRepository = oAuthOAuthRepository
        )

    /**
     * To provide [TabViewModelFactory] instance
     */
    @Provides
    fun providesNetworkLayer(
        internetHandler: InternetHandler,
        serviceProvider: ServiceProvider
    ): BottomTabRepository = BottomTabRepository(internetHandler, serviceProvider)

    @Provides
    fun providesBottomTabVFactory(bottomTabRepository: BottomTabRepository) =
        TabViewModelFactory(bottomTabRepository)


    /**
     * To provide [BottomLikeViewModelFactory] instance
     */
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


    /**
     * To provide [BottomSettingsViewModelFactory] instance
     */
    @Provides
    fun providesBottomSettingsPresenter(memoryCache: MemoryCache) =
        BottomSettingsViewModelFactory(memoryCache)
}
