package com.karthik.splash.homescreen.bottomliketab

import androidx.lifecycle.*
import com.karthik.splash.BuildConfig

import com.karthik.splash.homescreen.bottomliketab.network.BottomLikeTabRepository
import com.karthik.splash.homescreen.bottomliketab.network.LikeFeedNetworkState
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.storage.MemoryCache

class BottomLikeViewModelFactory(private val memoryCache: MemoryCache,
                                 private val userServiceRepository: BottomLikeTabRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = BottomLikeViewModel(memoryCache,userServiceRepository) as T
}


class BottomLikeViewModel(private val memoryCache: MemoryCache,
                          private val respository: BottomLikeTabRepository):ViewModel() {

    private val userscope = "public+read_user+read_photos+write_likes"

    val likefeeds: MutableLiveData<List<Photos>> = MutableLiveData()
    val networkstate:MutableLiveData<LikeFeedNetworkState> = MutableLiveData()
    val isuserloggedin:MutableLiveData<Boolean> = isloggedIn()

    val loginurl = "${BuildConfig.SPLASH_LOGIN_URL}?client_id=${BuildConfig.SPLASH_KEY}" +
            "&redirect_uri=${BuildConfig.SPLASH_LOGIN_CALLBACK}&response_type=code&scope=$userscope"


    fun getLikedPhotos(){
        networkstate.postValue(LikeFeedNetworkState.FeedNetworkLoading)
        respository.getUserLikedPhotos({photos->
            networkstate.postValue(LikeFeedNetworkState.FeedNetworkLoadSuccess)
            likefeeds.postValue(photos)
        },{error->
            networkstate.postValue(LikeFeedNetworkState.FeedNetworkError(error))
        })
    }

    private fun isloggedIn():MutableLiveData<Boolean>{
        val status = MutableLiveData<Boolean>()
        status.postValue(memoryCache.isUserLoggedIn())
        return status
    }

    override fun onCleared() {
        super.onCleared()
        respository.clearResources()
    }
}