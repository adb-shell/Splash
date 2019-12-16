package com.karthik.splash.homescreen.bottomhometab


import dagger.Module
import dagger.Provides

@Module
class BottomHomeTabModule(private val view: BottomHomeTabContract.view){

    @Provides
    fun providesView() = view

    @Provides
    fun providesBottomTabPresenter():BottomHomeTabContract.presenter = BottomHomeTabPresenter(view)
}