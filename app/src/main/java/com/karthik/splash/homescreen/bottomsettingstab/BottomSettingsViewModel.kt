package com.karthik.splash.homescreen.bottomsettingstab

import androidx.compose.runtime.State
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.network.IMemoryCache
import com.karthik.network.home.models.HomeScreenLoginState
import com.karthik.splash.homescreen.HomeClickEvents

@Suppress("UNCHECKED_CAST")
class BottomSettingsViewModelFactory(private val memoryCache: IMemoryCache) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        BottomSettingsViewModel(memoryCache) as T
}

class BottomSettingsViewModel(private val memoryCache: IMemoryCache) : ViewModel() {
    private val _settingEventClicked: MutableLiveData<HomeClickEvents> = MutableLiveData()
    val clickEvent = _settingEventClicked

    fun getUserName(): String? = memoryCache.getUserName()

    fun onClick(settingsEvent: HomeClickEvents) {
        if (settingsEvent == HomeClickEvents.LogoutClick) {
            logoutUser()
            return
        }
        _settingEventClicked.value = settingsEvent
    }

    fun getSettingsRowData(loginState: State<HomeScreenLoginState?>): List<HomeClickEvents> {
        val settingsRowsData = mutableListOf(
            HomeClickEvents.AboutClick,
            HomeClickEvents.DownloadsClick
        )

        if (loginState.value is HomeScreenLoginState.LoginSuccess) {
            settingsRowsData.add(
                0,
                HomeClickEvents.LoginClick
            )
            settingsRowsData.add(
                HomeClickEvents.LogoutClick
            )
        } else {
            settingsRowsData.add(
                0,
                HomeClickEvents.NotLoggedIn
            )
        }
        return settingsRowsData
    }

    private fun logoutUser() {
        memoryCache.logOutUser()
        _settingEventClicked.value = HomeClickEvents.LogoutClick
    }
}
