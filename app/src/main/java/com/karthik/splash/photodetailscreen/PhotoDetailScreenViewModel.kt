package com.karthik.splash.photodetailscreen

import androidx.lifecycle.*
import com.karthik.network.IMemoryCache
import com.karthik.network.photodetailscreen.IPhotoDetailScreenRepository
import com.karthik.network.photodetailscreen.models.*
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
    private val internalState: MutableLiveData<PhotoDetailsNetworkState> = MutableLiveData()

    val photolike: LiveData<LikeResponse> = like
    val photodetails: LiveData<PhotoDetailInfo> = details
    val networkState: LiveData<PhotoDetailsNetworkState> = internalState

    fun getPhotoDetail(id: String) {
        viewModelScope.launch {
            val photoInfoResponse = photoRepository.getPhotoInfo(id)
            if (photoInfoResponse is PhotoDetailsResponse.PhotoDetailsSuccessResponse) {
                internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                details.postValue(photoInfoResponse.photoDetail)
            } else {
                internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadError)
                //TODO:handle error
            }
        }
    }

    fun likeThePhoto(id: String) {
        viewModelScope.launch {
            val photoLikeResponse = photoRepository.likePhoto(id)
            if (photoLikeResponse is PhotoLikeResponse.PhotoDetailsSuccessResponse) {
                internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                like.postValue(photoLikeResponse.likeResponse)
            }else{
                internalState.postValue(PhotoDetailsNetworkState.PhotoLikeNetworkLoadError)
                //TODO:handle error
            }
        }
    }

    fun isUserLoggedIn() = memoryCache.isUserLoggedIn()
}
