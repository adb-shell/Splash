package com.karthik.splash.homescreen.bottomliketab

import androidx.lifecycle.*
import com.karthik.network.IMemoryCache
import com.karthik.network.home.bottomliketab.IBottomLikeTabRepository
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomliketab.models.UserLikedPhotoResponse
import com.karthik.splash.BuildConfig
import com.karthik.splash.homescreen.HomeClickEvents
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

    private val _screenStatus: MutableLiveData<ScreenStatus> = MutableLiveData()
    private val _clickEvent: MutableLiveData<HomeClickEvents> = MutableLiveData()

    val screenStatus: LiveData<ScreenStatus> = _screenStatus
    val clickEvent: LiveData<HomeClickEvents> = _clickEvent

    val loginurl = "${BuildConfig.SPLASH_LOGIN_URL}?client_id=${BuildConfig.SPLASH_KEY}" +
            "&redirect_uri=${BuildConfig.SPLASH_LOGIN_CALLBACK}&response_type=code&scope=$userscope"


    init {
        isloggedIn()
    }


    fun getLikedPhotos() {
        _screenStatus.postValue(ScreenStatus.ShowProgress)
        viewModelScope.launch {
            when (val response = respository.getUserLikedPhotos()) {
                is UserLikedPhotoResponse.UserLikedPhoto -> {
                    _screenStatus.postValue(
                        ScreenStatus.UserLikedPhotos(likedPhotos = response.photos)
                    )
                }
                is UserLikedPhotoResponse.UserLikedPhotoErrorState -> {
                    _screenStatus.postValue(ScreenStatus.ErrorFetchingPhotos(error = response.e))
                }
            }
        }
    }

    fun loginClicked(){
        _clickEvent.value = HomeClickEvents.NotLoggedIn
    }

    fun onPhotoItemClicked(photo: Photos){
        _clickEvent.value = HomeClickEvents.PhotoClick(photos = photo)
    }

    private fun isloggedIn() {
        if (memoryCache.isUserLoggedIn()) {
            memoryCache.getUserName()?.let { name ->
                _screenStatus.postValue(ScreenStatus.ScreenLoggedIn(name))
            } ?: _screenStatus.postValue(ScreenStatus.ScreenLoggedIn(""))
        } else {
            _screenStatus.postValue(ScreenStatus.ScreenNotLoggedIn)
        }
    }
}
