package com.karthik.splash.homescreen.bottomsettingstab

import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.network.IMemoryCache
import com.karthik.splash.homescreen.bottomliketab.ScreenStatus

@Suppress("UNCHECKED_CAST")
class BottomSettingsViewModelFactory(private val memoryCache: IMemoryCache)
    : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            BottomSettingsViewModel(memoryCache) as T
}

class BottomSettingsViewModel(private val memoryCache: IMemoryCache) : ViewModel() {
    private val _screenStatus: MutableLiveData<ScreenStatus> = MutableLiveData()
    private val _settingEventClicked: MutableLiveData<SettingsEvent> = MutableLiveData()
    val screenStatus: LiveData<ScreenStatus> = _screenStatus
    val settingTypeClicked = _settingEventClicked

    init {
        if (memoryCache.isUserLoggedIn()) {
            memoryCache.getUserName()?.let { name ->
                _screenStatus.postValue(ScreenStatus.ScreenLoggedIn(name))
            } ?: _screenStatus.postValue(ScreenStatus.ScreenNotLoggedIn)
        } else {
            _screenStatus.postValue(ScreenStatus.ScreenNotLoggedIn)
        }
    }

    fun getUserName(): String? {
        return if (screenStatus.value is ScreenStatus.ScreenLoggedIn) {
            (screenStatus.value as ScreenStatus.ScreenLoggedIn).username
        } else null
    }

    fun onClick(settingsEvent: SettingsEvent) {
        if(settingsEvent==SettingsEvent.Logout){
            logoutUser()
            return
        }
        _settingEventClicked.value = settingsEvent
    }

    fun getSettingsRowData(state: State<ScreenStatus?>): List<SettingsEvent> {

        val settingsRowsData = mutableListOf(
            SettingsEvent.About,
            SettingsEvent.Downloads
        )

        when (state.value) {
            is ScreenStatus.ScreenLoggedIn -> {
                settingsRowsData.add(
                    0,
                    SettingsEvent.LoggedIn
                )
                settingsRowsData.add(
                    SettingsEvent.Logout
                )
            }
            is ScreenStatus.ScreenNotLoggedIn -> {
                settingsRowsData.add(
                    0,
                    SettingsEvent.NotLoggedIn
                )
            }
        }
        return settingsRowsData
    }

    private fun logoutUser() {
        memoryCache.logOutUser()
        _screenStatus.postValue(ScreenStatus.ScreenNotLoggedIn)
    }
}
