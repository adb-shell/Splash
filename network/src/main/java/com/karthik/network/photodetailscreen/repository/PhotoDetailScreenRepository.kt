package com.karthik.network.photodetailscreen.repository

import com.karthik.network.photodetailscreen.IPhotoDetailScreenRepository
import com.karthik.network.photodetailscreen.models.PhotoDetailsResponse
import com.karthik.network.photodetailscreen.models.PhotoLikeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class PhotoDetailScreenRepository() :
    IPhotoDetailScreenRepository {

    @Inject lateinit var retrofit: Retrofit

    private val photoService: PhotoDetailsNetworkService by lazy {
        retrofit.create(PhotoDetailsNetworkService::class.java)
    }


    override suspend fun getPhotoInfo(id: String): PhotoDetailsResponse {
        return withContext(Dispatchers.IO) {
            val photoDetailsResponse = photoService.getPhotoInfo(id = id)
            if (photoDetailsResponse.isSuccessful) {
                photoDetailsResponse.body()?.let { photoDetailInfo ->
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
                    PhotoLikeResponse.PhotoDetailsSuccessResponse(likeResponse = likeResponse)
                } ?: reportLikeNetworkError()
            } else {
                reportLikeNetworkError()
            }
        }
    }

    private fun reportLikeNetworkError(): PhotoLikeResponse.PhotoLikeFailureResponse {
        return PhotoLikeResponse.PhotoLikeFailureResponse(IllegalStateException())
    }

    private fun reportDetailsNetworkError(): PhotoDetailsResponse.PhotoDetailsFailureResponse {
        return PhotoDetailsResponse.PhotoDetailsFailureResponse(IllegalStateException())
    }
}
