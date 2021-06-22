package com.karthik.splash.homescreen


import androidx.lifecycle.*
import com.karthik.network.IMemoryCache
import com.karthik.network.home.IHomeScreenOAuthRepository
import com.karthik.network.home.models.HomeScreenLoginState
import com.karthik.network.home.models.OAuthBody
import kotlinx.coroutines.launch
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class HomeScreenViewModelFactory(
    private val homeScreenOAuthRepository: IHomeScreenOAuthRepository,
    private val memoryCache: IMemoryCache,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            HomeScreenViewModel(memoryCache, homeScreenOAuthRepository) as T
}

class HomeScreenViewModel @Inject constructor(
        private val memoryCache: IMemoryCache,
        private val homeScreenOAuthRepository: IHomeScreenOAuthRepository
) : ViewModel() {
    private val state: MutableLiveData<HomeScreenLoginState> = MutableLiveData()
    val userloginstate: LiveData<HomeScreenLoginState> = state

    fun getUserInfo(code: String?) {
        if (code.isNullOrEmpty()) {
            state.value = HomeScreenLoginState.LoginFailed(IllegalArgumentException())
            return
        }

        viewModelScope.launch {
            state.value = homeScreenOAuthRepository.postOAuth(OAuthBody(code))
            when(state.value){
                is HomeScreenLoginState.LoginSuccess->{
                    memoryCache.setUserLoggedIn()
                    val auth = (state.value as HomeScreenLoginState.LoginSuccess).userAuth
                    memoryCache.setAuthCode(auth.accessToken)
                }
            }
        }
    }
}
