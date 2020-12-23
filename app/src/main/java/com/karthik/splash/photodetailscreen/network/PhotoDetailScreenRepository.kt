package com.karthik.splash.photodetailscreen.network

import androidx.lifecycle.MutableLiveData
import com.karthik.splash.photodetailscreen.IPhotoDetailScreenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

class PhotoDetailScreenRepository(private val photoService: PhotoService) :
    IPhotoDetailScreenRepository {

    private val internalState: MutableLiveData<PhotoDetailsNetworkState> = MutableLiveData()

    override fun getPhotoDetailNetworkState() =
            internalState


    override suspend fun getPhotoInfo(id: String): PhotoDetailsResponse {
        return withContext(Dispatchers.IO) {
            val photoDetailsResponse = photoService.getPhotoInfo(id = id)
            if (photoDetailsResponse.isSuccessful) {
                photoDetailsResponse.body()?.let { photoDetailInfo ->
                    internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                    PhotoDetailsResponse.PhotoDetailsSuccessResponse(photoDetail = photoDetailInfo)
                } ?: reportDetailsNetworkError()
            } else {
                reportDetailsNetworkError()
            }
        }
    }

    override suspend fun likePhoto(id: String): PhotoLikeResponse {
        return withContext(Dispatchers.IO) {
            val photoLikeResponse = photoService.likePhoto(id)
            if (photoLikeResponse.isSuccessful) {
                photoLikeResponse.body()?.let { likeResponse ->
                    internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                    PhotoLikeResponse.PhotoDetailsSuccessResponse(likeResponse = likeResponse)
                } ?: reportLikeNetworkError()
            } else {
                reportLikeNetworkError()
            }
        }
    }

    private fun reportLikeNetworkError(): PhotoLikeResponse.PhotoDetailsFailureResponse {
        internalState.postValue(PhotoDetailsNetworkState.PhotoLikeNetworkLoadError)
        return PhotoLikeResponse.PhotoDetailsFailureResponse(IllegalStateException())
    }

    private fun reportDetailsNetworkError(): PhotoDetailsResponse.PhotoDetailsFailureResponse {
        internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadError)
        return PhotoDetailsResponse.PhotoDetailsFailureResponse(IllegalStateException())
    }
}
