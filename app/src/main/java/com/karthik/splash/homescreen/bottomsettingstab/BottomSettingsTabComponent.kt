package com.karthik.splash.homescreen.bottomsettingstab

import dagger.Subcomponent

/**
 * Created by karthikrk on 03/12/17.
 */
@BottomSettingsTabScope
@Subcomponent(modules = [BottomSettingsTabModule::class])
interface BottomSettingsTabComponent {
    fun inject(bottomSettingsTabFragment: BottomSettingsTabFragment)
}
