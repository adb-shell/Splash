package com.karthik.splash.homescreen.bottomtab

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.homescreen.bottomtab.network.BottomTabRepository
import com.karthik.splash.misc.Utils
import javax.inject.Inject

class BottomTabViewModel @Inject constructor(private val isCacheAvailable:Boolean,
                                             private val bottomTabRepository: BottomTabRepository):ViewModel() {

    val newfeeds:MutableLiveData<PhotoFeedState> = MutableLiveData()
    val featuredfeeds:MutableLiveData<PhotoFeedState> = MutableLiveData()
    val trendingfeeds:MutableLiveData<PhotoFeedState> = MutableLiveData()

    //TODO:pagination implementation
    fun getPaginatedFeeds(mode: BottomTabTypes?, pageSize: Int){

    }

    fun getFeeds(mode: BottomTabTypes?){

        requireNotNull(mode) { "mode cannot be null" }

        val type:String = when(mode){
            is BottomTabTypes.New-> BottomTabRepository.SORT_BY_LATEST
            is BottomTabTypes.Featured-> BottomTabRepository.SORT_BY_POPULAR
            is BottomTabTypes.Trending-> BottomTabRepository.SORT_BY_OLDEST
            else -> ""
        }

        bottomTabRepository.getFeeds(type = type,successhander = {photos->
            when(mode){
                is BottomTabTypes.New->newfeeds.value = PhotoFeedState.FeedState(photos)
                is BottomTabTypes.Featured->featuredfeeds.value = PhotoFeedState.FeedState(photos)
                is BottomTabTypes.Trending->trendingfeeds.value = PhotoFeedState.FeedState(photos)
            }
        },errorhandler = {error->
            when(mode){
               is BottomTabTypes.New->manageErrors(error,newfeeds)
               is BottomTabTypes.Featured->manageErrors(error,featuredfeeds)
               is BottomTabTypes.Trending->manageErrors(error,trendingfeeds)
            }
        })
    }

    private fun manageErrors(error: Throwable,feed:MutableLiveData<PhotoFeedState>) {
        if(Utils.getErrorType(error)== Utils.NetworkErrorType.OFFLINE){
            feed.value =  PhotoFeedState.FeedNoInternet
            return
        }
        feed.value =  PhotoFeedState.FeedError(error)
    }

    override fun onCleared() {
        super.onCleared()
        bottomTabRepository.clearResources()
    }

    class BottomTabViewModelFactory(private val isCacheAvailable:Boolean,
                                    private val bottomTabRepository: BottomTabRepository):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T
                = BottomTabViewModel(isCacheAvailable,bottomTabRepository) as T
    }
}