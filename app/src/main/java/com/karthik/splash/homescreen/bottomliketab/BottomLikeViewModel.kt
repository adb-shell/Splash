package com.karthik.splash.homescreen.bottomliketab

import androidx.lifecycle.*
import com.karthik.network.IMemoryCache
import com.karthik.network.home.bottomliketab.IBottomLikeTabRepository
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomliketab.models.UserLikedPhotoResponse
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
    private val _networkNetworkStatusLike: MutableLiveData<LikeScreenNetworkStatus> = MutableLiveData()
    private val _clickEvent: MutableLiveData<HomeClickEvents> = MutableLiveData()

    val networkNetworkStatusLike: LiveData<LikeScreenNetworkStatus> = _networkNetworkStatusLike
    val clickEvent: LiveData<HomeClickEvents> = _clickEvent


    fun getLikedPhotos() {
        _networkNetworkStatusLike.postValue(LikeScreenNetworkStatus.ShowProgress)
        viewModelScope.launch {
            when (val response = respository.getUserLikedPhotos()) {
                is UserLikedPhotoResponse.UserLikedPhoto -> {
                    _networkNetworkStatusLike.postValue(
                        LikeScreenNetworkStatus.UserLikedPhotos(likedPhotos = response.photos)
                    )
                }
                is UserLikedPhotoResponse.UserLikedPhotoErrorState -> {
                    _networkNetworkStatusLike.postValue(LikeScreenNetworkStatus.ErrorFetchingPhotos(error = response.e))
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
}
