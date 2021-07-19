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
    private val _settingTypeClicked: MutableLiveData<SettingsType> = MutableLiveData()
    val screenStatus: LiveData<ScreenStatus> = _screenStatus
    val settingTypeClicked = _settingTypeClicked

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

    fun onClick(settingsType: SettingsType) {
        if(settingsType==SettingsType.Logout){
            logoutUser()
            return
        }
        _settingTypeClicked.value = settingsType
    }

    fun getSettingsRowData(state: State<ScreenStatus?>): List<SettingsType> {

        val settingsRowsData = mutableListOf(
            SettingsType.About,
            SettingsType.Downloads
        )

        when (state.value) {
            is ScreenStatus.ScreenLoggedIn -> {
                settingsRowsData.add(
                    0,
                    SettingsType.LoggedIn
                )
                settingsRowsData.add(
                    SettingsType.Logout
                )
            }
            is ScreenStatus.ScreenNotLoggedIn -> {
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
        _screenStatus.postValue(ScreenStatus.ScreenNotLoggedIn)
    }
}
