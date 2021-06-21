package com.karthik.splash.homescreen.di

import com.karthik.network.home.di.HomeScreenNetworkModule
import com.karthik.splash.homescreen.HomeScreen
import dagger.Subcomponent

/**
 * Created by karthikrk on 14/11/17.
 */
@HomeScreenScope
@Subcomponent(modules = [HomeScreenNetworkModule::class,HomeScreenModule::class])
interface HomeScreenComponent {
    fun inject(homeScreen: HomeScreen)
}
