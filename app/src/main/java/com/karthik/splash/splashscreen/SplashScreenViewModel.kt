package com.karthik.splash.splashscreen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.misc.Utils
import com.karthik.splash.storage.MemoryCache
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SplashScreenViewModelFactory(private val memoryCache: MemoryCache,
                                   private val internetHandler: InternetHandler): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>)
            = SplashScreenViewModel(memoryCache,internetHandler)as T
}

class SplashScreenViewModel constructor(val memoryCache: MemoryCache,
                                        private val internetHandler: InternetHandler):ViewModel() {
    val splashscreenstate:LiveData<SplashScreenState> = getViewState()

    private fun getViewState():LiveData<SplashScreenState>{
        val state = MutableLiveData<SplashScreenState>()
        //no internet
        if (!internetHandler.isInternetAvailable() && !memoryCache.isCacheAvail()) {
            state.value = SplashScreenState.NoInternetScreen
            return state
        }
        //cache avail
        if(!internetHandler.isInternetAvailable() && memoryCache.isCacheAvail()){
            state.value = SplashScreenState.CachedDashBoardScreen
            return state
        }
        //fresh data
        state.value = SplashScreenState.FreshDashBoardScreen
        return state
    }
}