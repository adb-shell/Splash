package com.karthik.splash.homescreen.bottomsettingstab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.models.UserStatus
import com.karthik.splash.storage.IMemoryCache
import com.karthik.splash.storage.MemoryCache

@Suppress("UNCHECKED_CAST")
class BottomSettingsViewModelFactory(private val memoryCache:IMemoryCache):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = BottomSettingsViewModel(memoryCache) as T
}

class BottomSettingsViewModel(private val memoryCache:IMemoryCache): ViewModel() {
    private val _userStatus: MutableLiveData<UserStatus> = MutableLiveData()
    val userStatus: LiveData<UserStatus> = _userStatus

    init {
        if(memoryCache.isUserLoggedIn()){
            memoryCache.getUserName()?.let { name ->
                _userStatus.postValue(UserStatus.UserLoggedIn(name))
            } ?: _userStatus.postValue(UserStatus.UserNotLoggedIn)
        }else{
            _userStatus.postValue(UserStatus.UserNotLoggedIn)
        }
    }

    fun logoutUser(){
        memoryCache.logOutUser()
        _userStatus.postValue(UserStatus.UserNotLoggedIn)
    }
}