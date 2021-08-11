package com.karthik.splash.homescreen.bottomhometab.tab.di

import com.karthik.splash.homescreen.bottomhometab.BottomHomeTabFragment
import dagger.Subcomponent

/**
 * Created by karthikrk on 16/11/17.
 */
@TabScope
@Subcomponent(modules = [TabModule::class])
interface TabComponent {
    fun inject(homeTabFeeds: BottomHomeTabFragment)
}
