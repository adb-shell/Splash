package com.karthik.splash.photodetailscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import com.karthik.splash.photodetailscreen.network.PhotoDetailScreenRepository
import com.karthik.splash.storage.Cache

class PhotoDetailScreenViewModelFactory(private val cache: Cache,private val photoRepository: PhotoDetailScreenRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T  = PhotoDetailScreenViewModel(cache,photoRepository) as T
}


class PhotoDetailScreenViewModel(private val cache: Cache,
                                 private val photoRepository: PhotoDetailScreenRepository):ViewModel() {

    val photodetails:MutableLiveData<PhotoDetailInfo> = MutableLiveData()
    val photolike:MutableLiveData<LikeResponse> = MutableLiveData()

    fun getPhotoDetail(id:String){
        photoRepository.getPhotoInfo(id) { photoinfo->
            photodetails.postValue(photoinfo)
        }
    }

    fun likeThePhoto(id:String){
        photoRepository.likePhoto(id) { likeResponse ->
            photolike.postValue(likeResponse)
        }
    }

    fun isUserLoggedIn() = cache.isUserLoggedIn()

    fun getnetworkState() = photoRepository.photoDetailsNetworkState

    override fun onCleared() {
        super.onCleared()
        photoRepository.clearResources()
    }
}