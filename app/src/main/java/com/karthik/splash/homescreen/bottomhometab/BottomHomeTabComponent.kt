package com.karthik.splash.homescreen.bottomhometab

import dagger.Subcomponent

/**
 * Created by karthikrk on 15/11/17.
 */
@BottomHomeTabScope
@Subcomponent(modules = [BottomHomeTabModule::class])
interface BottomHomeTabComponent {
    fun inject(feedsHome: BottomHomeTabFragment)
}
