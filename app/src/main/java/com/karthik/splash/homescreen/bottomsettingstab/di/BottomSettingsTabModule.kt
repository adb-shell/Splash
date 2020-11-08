package com.karthik.splash.homescreen.bottomsettingstab.di


import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsViewModelFactory
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides

@Module
class BottomSettingsTabModule {

    @Provides
    fun providesBottomSettingsPresenter(memoryCache: MemoryCache) =
            BottomSettingsViewModelFactory(memoryCache)
}