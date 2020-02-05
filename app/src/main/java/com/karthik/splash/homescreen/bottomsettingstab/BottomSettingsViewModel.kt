package com.karthik.splash.homescreen.bottomsettingstab

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.storage.MemoryCache

class BottomSettingsViewModelFactory(private val memoryCache:MemoryCache):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = BottomSettingsViewModel(memoryCache) as T
}

class BottomSettingsViewModel(private val memoryCache:MemoryCache): ViewModel() {
    val isuserloggedin: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isuserloggedin.postValue(memoryCache.isUserLoggedIn())
    }

    fun getUserName():String? = memoryCache.getUserName()

    fun logoutUser(){
        memoryCache.logOutUser()
        isuserloggedin.postValue(memoryCache.isUserLoggedIn())
    }
}