package com.karthik.splash.homescreen

import dagger.Subcomponent

/**
 * Created by karthikrk on 14/11/17.
 */
@HomeScreenScope
@Subcomponent(modules = [HomeScreenModule::class])
interface HomeScreenComponent {
    fun inject(homeScreen: HomeScreen)
}
