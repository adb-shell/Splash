package com.karthik.splash.homescreen.bottomtab

import dagger.Subcomponent

/**
 * Created by karthikrk on 16/11/17.
 */
@BottomTabScope
@Subcomponent(modules = [BottomTabModule::class])
interface BottomTabComponent {
    fun inject(homeTabFeeds: BottomTabFragment)
}
