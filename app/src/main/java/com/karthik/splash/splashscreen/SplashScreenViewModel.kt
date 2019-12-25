package com.karthik.splash.splashscreen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.misc.Utils
import com.karthik.splash.storage.Cache
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(val cache: Cache,val splashViewContract: SplashViewContract):ViewModel() {

    val shouldshowcache:LiveData<SplashScreenState> = getViewState()

    private fun getViewState():LiveData<SplashScreenState>{
        val state = MutableLiveData<SplashScreenState>()
        //no internet
        if (!Utils.isInternetAvailable(splashViewContract.getSplashScreenContext()) && !cache.isCacheAvail()) {
            state.value = SplashScreenState.NoInternetScreen
            return state
        }
        //cache avail
        if(!Utils.isInternetAvailable(splashViewContract.getSplashScreenContext()) && cache.isCacheAvail()){
            state.value = SplashScreenState.CachedDashBoardScreen
            return state
        }
        //fresh data
        state.value = SplashScreenState.FreshDashBoardScreen
        return state
    }

    class SplashScreenViewModelFactory(private val cache: Cache,private val splashViewContract: SplashViewContract): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>) = SplashScreenViewModel(cache,splashViewContract)as T
    }
}