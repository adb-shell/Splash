package com.karthik.splash.photodetailscreen

import androidx.lifecycle.*
import com.karthik.network.IMemoryCache
import com.karthik.network.photodetailscreen.IPhotoDetailScreenRepository
import com.karthik.network.photodetailscreen.models.PhotoDetailsResponse
import com.karthik.network.photodetailscreen.models.PhotoLikeResponse
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

    private val _screenStatus: MutableLiveData<ScreenStatus> = MutableLiveData()
    private val _eventClicked: MutableLiveData<PhotoDetailEvent> = MutableLiveData()
    val screenStatus: LiveData<ScreenStatus> = _screenStatus
    val buttonClicked: LiveData<PhotoDetailEvent> = _eventClicked

    fun getPhotoDetail(id: String) {
        _screenStatus.postValue(ScreenStatus.ShowProgress)
        viewModelScope.launch {
            val photoInfoResponse = photoRepository.getPhotoInfo(id)
            if (photoInfoResponse is PhotoDetailsResponse.PhotoDetailsSuccessResponse) {
                _screenStatus.postValue(
                    ScreenStatus.PhotoDetailScreen(photoInfo = photoInfoResponse.photoDetail)
                )
            } else {
                _screenStatus.postValue(ScreenStatus.ErrorFetchingPhotoDetail)
            }
        }
    }

    fun likeThePhoto(id: String) {
        if (id.isNullOrEmpty())
            return

        viewModelScope.launch {
            val photoLikeResponse = photoRepository.likePhoto(id)
            if (photoLikeResponse is PhotoLikeResponse.PhotoDetailsSuccessResponse) {
                _screenStatus.postValue(ScreenStatus.PhotoLikeSuccess)
            }else{
                _screenStatus.postValue(ScreenStatus.ErrorLikingPhoto)
            }
        }
    }

    fun shareClicked() = _eventClicked.postValue(PhotoDetailEvent.SHARE_CLICK)

    fun downloadClicked() = _eventClicked.postValue(PhotoDetailEvent.DOWNLOAD_CLICK)

    fun isUserLoggedIn() = memoryCache.isUserLoggedIn()
}
