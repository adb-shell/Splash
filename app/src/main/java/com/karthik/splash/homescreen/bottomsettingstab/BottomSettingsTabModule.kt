package com.karthik.splash.homescreen.bottomsettingstab


import com.karthik.splash.Storage.Cache
import dagger.Module
import dagger.Provides

@Module
class BottomSettingsTabModule(private val view:BottomSettingsTabContract.view) {

    @Provides
    fun providesBottomSettingsView() = view

    @Provides
    fun providesBottomSettingsPresenter(cache: Cache):BottomSettingsTabContract.presenter = BottomSettingsTabPresenter(view,cache)
}