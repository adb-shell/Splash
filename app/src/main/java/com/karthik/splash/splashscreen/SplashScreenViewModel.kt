package com.karthik.splash.splashscreen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.network.IInternetHandler
import com.karthik.network.IMemoryCache

@Suppress("UNCHECKED_CAST")
class SplashScreenViewModelFactory(
    private val memoryCache: IMemoryCache,
    private val internetHandler: IInternetHandler
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            SplashScreenViewModel(memoryCache, internetHandler) as T
}

class SplashScreenViewModel constructor(
        private val memoryCache: IMemoryCache,
        private val internetHandler: IInternetHandler
) : ViewModel() {
    val splashscreenstate: LiveData<SplashScreenState> = getViewState()

    private fun getViewState(): LiveData<SplashScreenState> {
        val state = MutableLiveData<SplashScreenState>()
        //no internet
        if (!internetHandler.isInternetAvailable() && !memoryCache.isCacheAvail()) {
            state.value = SplashScreenState.NoInternetScreen
            return state
        }
        //cache avail
        if (!internetHandler.isInternetAvailable() && memoryCache.isCacheAvail()) {
            state.value = SplashScreenState.CachedDashBoardScreen
            return state
        }
        //fresh data
        state.value = SplashScreenState.FreshDashBoardScreen
        return state
    }
}