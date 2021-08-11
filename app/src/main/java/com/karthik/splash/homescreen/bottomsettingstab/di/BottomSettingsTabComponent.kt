package com.karthik.splash.homescreen.bottomsettingstab.di

import androidx.compose.material.ExperimentalMaterialApi
import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsTabFragment
import dagger.Subcomponent

/**
 * Created by karthikrk on 03/12/17.
 */
@ExperimentalMaterialApi
@BottomSettingsTabScope
@Subcomponent(modules = [BottomSettingsTabModule::class])
interface BottomSettingsTabComponent {
    fun inject(bottomSettingsTabFragment: BottomSettingsTabFragment)
}
