package com.karthik.splash.photodetailscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import com.karthik.splash.storage.IMemoryCache

@Suppress("UNCHECKED_CAST")
class PhotoDetailScreenViewModelFactory(private val memoryCache: IMemoryCache,
                                        private val photoRepository: IPhotoDetailScreenRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            PhotoDetailScreenViewModel(memoryCache, photoRepository) as T
}


class PhotoDetailScreenViewModel(private val memoryCache: IMemoryCache,
                                 private val photoRepository: IPhotoDetailScreenRepository) : ViewModel() {
    private val details: MutableLiveData<PhotoDetailInfo> = MutableLiveData()
    private val like: MutableLiveData<LikeResponse> = MutableLiveData()

    val photolike: LiveData<LikeResponse> = like
    val photodetails: LiveData<PhotoDetailInfo> = details

    fun getPhotoDetail(id: String) {
        photoRepository.getPhotoInfo(id,{ photoinfo ->
            details.postValue(photoinfo)
        },{
            //TODO:handle error
        })
    }

    fun likeThePhoto(id: String) {
        photoRepository.likePhoto(id,{ likeResponse ->
            like.postValue(likeResponse)
        },{
            //TODO:handle error
        })
    }

    fun isUserLoggedIn() = memoryCache.isUserLoggedIn()

    fun getnetworkState() = photoRepository.getPhotoDetailNetworkState()

    override fun onCleared() {
        super.onCleared()
        photoRepository.clearResources()
    }
}