package com.karthik.splash.homescreen.bottomliketab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.BuildConfig
import com.karthik.splash.homescreen.bottomliketab.network.IBottomLikeTabRepository
import com.karthik.splash.homescreen.bottomliketab.network.LikeFeedNetworkState
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.models.UserStatus
import com.karthik.splash.storage.IMemoryCache

@Suppress("UNCHECKED_CAST")
class BottomLikeViewModelFactory(private val memoryCache: IMemoryCache,
                                 private val userServiceRepository: IBottomLikeTabRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = BottomLikeViewModel(memoryCache,userServiceRepository) as T
}


class BottomLikeViewModel(private val memoryCache: IMemoryCache,
                          private val respository: IBottomLikeTabRepository):ViewModel() {

    private val userscope = "public+read_user+read_photos+write_likes"

    private val _likefeeds: MutableLiveData<List<Photos>> = MutableLiveData()
    private val _networkstate:MutableLiveData<LikeFeedNetworkState> = MutableLiveData()
    private val _isuserloggedin:MutableLiveData<UserStatus> = MutableLiveData()

    val likefeeds: LiveData<List<Photos>> = _likefeeds
    val networkstate: LiveData<LikeFeedNetworkState> = _networkstate
    val isuserloggedin: LiveData<UserStatus> = _isuserloggedin

    val loginurl = "${BuildConfig.SPLASH_LOGIN_URL}?client_id=${BuildConfig.SPLASH_KEY}" +
            "&redirect_uri=${BuildConfig.SPLASH_LOGIN_CALLBACK}&response_type=code&scope=$userscope"


    init {
        isloggedIn()
    }


    fun getLikedPhotos(){
        _networkstate.postValue(LikeFeedNetworkState.FeedNetworkLoading)
        respository.getUserLikedPhotos({photos->
            _networkstate.postValue(LikeFeedNetworkState.FeedNetworkLoadSuccess)
            _likefeeds.postValue(photos)
        },{error->
            _networkstate.postValue(LikeFeedNetworkState.FeedNetworkError(error))
        })
    }

    private fun isloggedIn(){
        if(memoryCache.isUserLoggedIn()){
            memoryCache.getUserName()?.let { name ->
                _isuserloggedin.postValue(UserStatus.UserLoggedIn(name))
            } ?: _isuserloggedin.postValue(UserStatus.UserNotLoggedIn)
        }else{
            _isuserloggedin.postValue(UserStatus.UserNotLoggedIn)
        }
    }

    override fun onCleared() {
        super.onCleared()
        respository.clearResources()
    }
}