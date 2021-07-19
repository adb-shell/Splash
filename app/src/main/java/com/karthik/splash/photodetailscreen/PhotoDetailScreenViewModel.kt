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

    private val _screenStatus: MutableLiveData<ScreenStatus> = MutableLiveData()
    val screenStatus: LiveData<ScreenStatus> = _screenStatus

    fun getPhotoDetail(id: String) {
        _screenStatus.postValue(ScreenStatus.ShowProgress)
        viewModelScope.launch {
            val photoInfoResponse = photoRepository.getPhotoInfo(id)
            if (photoInfoResponse is PhotoDetailsResponse.PhotoDetailsSuccessResponse) {
                _screenStatus.postValue(ScreenStatus.PhotoDetailScreen(photoInfo = photoInfoResponse.photoDetail))
            } else {
                _screenStatus.postValue(ScreenStatus.ErrorFetchingPhotoDetail)
            }
        }
    }

    fun likeThePhoto(id: String) {
        viewModelScope.launch {
            val photoLikeResponse = photoRepository.likePhoto(id)
            if (photoLikeResponse is PhotoLikeResponse.PhotoDetailsSuccessResponse) {
                _screenStatus.postValue(ScreenStatus.PhotoLikeSuccess)
            }else{
                _screenStatus.postValue(ScreenStatus.ErrorLikingPhoto)
            }
        }
    }

    fun isUserLoggedIn() = memoryCache.isUserLoggedIn()
}
