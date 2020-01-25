package com.karthik.splash.homescreen.bottomsettingstab.di



import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsViewModelFactory
import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides

@Module
class BottomSettingsTabModule{

    @Provides
    fun providesBottomSettingsPresenter(cache: Cache) = BottomSettingsViewModelFactory(cache)
}