package com.karthik.splash.homescreen.bottomsettingstab

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.storage.Cache

class BottomSettingsViewModelFactory(private val cache:Cache):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = BottomSettingsViewModel(cache) as T
}

class BottomSettingsViewModel(private val cache:Cache): ViewModel() {
    val isuserloggedin: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isuserloggedin.postValue(cache.isUserLoggedIn())
    }

    fun getUserName():String? = cache.getUserName()

    fun logoutUser(){
        cache.logOutUser()
        isuserloggedin.postValue(cache.isUserLoggedIn())
    }
}