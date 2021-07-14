package com.karthik.splash.homescreen.bottomsettingstab

import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.network.IMemoryCache
import com.karthik.splash.R
import com.karthik.splash.models.UserStatus

@Suppress("UNCHECKED_CAST")
class BottomSettingsViewModelFactory(private val memoryCache: IMemoryCache)
    : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            BottomSettingsViewModel(memoryCache) as T
}

class BottomSettingsViewModel(private val memoryCache: IMemoryCache) : ViewModel() {
    private val _userStatus: MutableLiveData<UserStatus> = MutableLiveData()
    private val _settingTypeClicked: MutableLiveData<SettingsType> = MutableLiveData()
    val userStatus: LiveData<UserStatus> = _userStatus
    val settingTypeClicked = _settingTypeClicked

    init {
        if (memoryCache.isUserLoggedIn()) {
            memoryCache.getUserName()?.let { name ->
                _userStatus.postValue(UserStatus.UserLoggedIn(name))
            } ?: _userStatus.postValue(UserStatus.UserNotLoggedIn)
        } else {
            _userStatus.postValue(UserStatus.UserNotLoggedIn)
        }
    }

    fun getUserName(): String? {
        return when (userStatus.value) {
            is UserStatus.UserLoggedIn -> {
                (userStatus.value as UserStatus.UserLoggedIn).username
            }
            else -> null
        }
    }

    fun onClick(settingsType: SettingsType) {
        if(settingsType==SettingsType.Logout){
            logoutUser()
            return
        }
        _settingTypeClicked.value = settingsType
    }

    fun getSettingsRowData(state: State<UserStatus?>): List<SettingsType> {

        val settingsRowsData = mutableListOf(
            SettingsType.About,
            SettingsType.Downloads
        )

        when (state.value) {
            is UserStatus.UserLoggedIn -> {
                settingsRowsData.add(
                    0,
                    SettingsType.LoggedIn
                )
                settingsRowsData.add(
                    SettingsType.Logout
                )
            }
            is UserStatus.UserNotLoggedIn -> {
                settingsRowsData.add(
                    0,
                    SettingsType.NotLoggedIn
                )
            }
        }
        return settingsRowsData
    }

    private fun logoutUser() {
        memoryCache.logOutUser()
        _userStatus.postValue(UserStatus.UserNotLoggedIn)
    }
}
