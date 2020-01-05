package com.karthik.splash.homescreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.homescreen.network.HomeScreenOAuthRepository
import com.karthik.splash.models.oauth.OAuthBody
import com.karthik.splash.models.oauth.UserAuth
import com.karthik.splash.storage.Cache
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(private val cache: Cache,
                                              private val homeScreenOAuthRepository: HomeScreenOAuthRepository): ViewModel() {
    val userloginstate:MutableLiveData<HomeScreenLoginState> = MutableLiveData()
    private val disposable = CompositeDisposable()

    fun getUserInfo(code:String?){
        disposable.add(homeScreenOAuthRepository.postOAuth(OAuthBody(code)).subscribeWith(object: DisposableSingleObserver<UserAuth>(){
            override fun onSuccess(userAuth: UserAuth) {
                cache.setUserLoggedIn()
                cache.setAuthCode(userAuth.accessToken)
                userloginstate.value = HomeScreenLoginState.LoginSuccess(userAuth)
            }

            override fun onError(e: Throwable) {
                userloginstate.value = HomeScreenLoginState.LoginFailed(e)
            }
        }))
    }

    override fun onCleared() {
        super.onCleared()
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }

    class HomeScreenViewModelFactory(private val cache: Cache,
                                     private val homeScreenOAuthRepository: HomeScreenOAuthRepository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = HomeScreenViewModel(cache,homeScreenOAuthRepository) as T
    }
}