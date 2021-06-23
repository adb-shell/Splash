package com.karthik.splash.homescreen.bottomliketab

import androidx.lifecycle.*
import com.karthik.network.IMemoryCache
import com.karthik.network.home.bottomliketab.IBottomLikeTabRepository
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomliketab.models.UserLikedPhotoResponse
import com.karthik.splash.BuildConfig
import com.karthik.splash.models.UserStatus
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class BottomLikeViewModelFactory(
    private val memoryCache: IMemoryCache,
    private val userServiceRepository:
    IBottomLikeTabRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            BottomLikeViewModel(memoryCache, userServiceRepository) as T
}


class BottomLikeViewModel(
        private val memoryCache: IMemoryCache,
        private val respository: IBottomLikeTabRepository
) : ViewModel() {

    private val userscope = "public+read_user+read_photos+write_likes"

    private val _likefeeds: MutableLiveData<List<Photos>> = MutableLiveData()
    private val _networkstate: MutableLiveData<LikeFeedNetworkState> = MutableLiveData()
    private val _isuserloggedin: MutableLiveData<UserStatus> = MutableLiveData()

    val likefeeds: LiveData<List<Photos>> = _likefeeds
    val networkstate: LiveData<LikeFeedNetworkState> = _networkstate
    val isuserloggedin: LiveData<UserStatus> = _isuserloggedin

    val loginurl = "${BuildConfig.SPLASH_LOGIN_URL}?client_id=${BuildConfig.SPLASH_KEY}" +
            "&redirect_uri=${BuildConfig.SPLASH_LOGIN_CALLBACK}&response_type=code&scope=$userscope"


    init {
        isloggedIn()
    }


    fun getLikedPhotos() {
        _networkstate.postValue(LikeFeedNetworkState.FeedNetworkLoading)
        viewModelScope.launch {
            when (val response = respository.getUserLikedPhotos()) {
                is UserLikedPhotoResponse.UserLikedPhoto -> {
                    _networkstate.postValue(LikeFeedNetworkState.FeedNetworkLoadSuccess)
                    _likefeeds.postValue(response.photos)
                }
                is UserLikedPhotoResponse.UserLikedPhotoErrorState -> {
                    _networkstate.postValue(LikeFeedNetworkState.FeedNetworkError(response.e))
                }
            }
        }
    }

    private fun isloggedIn() {
        if (memoryCache.isUserLoggedIn()) {
            memoryCache.getUserName()?.let { name ->
                _isuserloggedin.postValue(UserStatus.UserLoggedIn(name))
            } ?: _isuserloggedin.postValue(UserStatus.UserLoggedIn(""))
        } else {
            _isuserloggedin.postValue(UserStatus.UserNotLoggedIn)
        }
    }
}
