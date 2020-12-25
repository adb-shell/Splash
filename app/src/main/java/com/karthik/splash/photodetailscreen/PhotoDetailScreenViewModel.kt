package com.karthik.splash.photodetailscreen

import androidx.lifecycle.*
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import com.karthik.splash.photodetailscreen.network.PhotoDetailsResponse
import com.karthik.splash.photodetailscreen.network.PhotoLikeResponse
import com.karthik.splash.storage.IMemoryCache
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class PhotoDetailScreenViewModelFactory(
        private val memoryCache: IMemoryCache,
        private val photoRepository: IPhotoDetailScreenRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            PhotoDetailScreenViewModel(memoryCache, photoRepository) as T
}


class PhotoDetailScreenViewModel(
        private val memoryCache: IMemoryCache,
        private val photoRepository: IPhotoDetailScreenRepository
) : ViewModel() {
    private val details: MutableLiveData<PhotoDetailInfo> = MutableLiveData()
    private val like: MutableLiveData<LikeResponse> = MutableLiveData()

    val photolike: LiveData<LikeResponse> = like
    val photodetails: LiveData<PhotoDetailInfo> = details

    fun getPhotoDetail(id: String) {
        viewModelScope.launch {
            val photoInfoResponse = photoRepository.getPhotoInfo(id)
            if (photoInfoResponse is PhotoDetailsResponse.PhotoDetailsSuccessResponse) {
                details.postValue(photoInfoResponse.photoDetail)
            } else {
                //TODO:handle error
            }
        }
    }

    fun likeThePhoto(id: String) {
        viewModelScope.launch {
            val photoLikeResponse = photoRepository.likePhoto(id)
            if (photoLikeResponse is PhotoLikeResponse.PhotoDetailsSuccessResponse) {
                like.postValue(photoLikeResponse.likeResponse)
            }else{
                //TODO:handle error
            }
        }
    }

    fun isUserLoggedIn() =
            memoryCache.isUserLoggedIn()

    fun getnetworkState() =
            photoRepository.getPhotoDetailNetworkState()
}
