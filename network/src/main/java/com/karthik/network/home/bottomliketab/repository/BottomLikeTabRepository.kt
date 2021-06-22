package com.karthik.network.home.bottomliketab.repository

import com.karthik.network.IInternetHandler
import com.karthik.network.IMemoryCache
import com.karthik.network.UserOfflineException
import com.karthik.network.home.bottomliketab.IBottomLikeTabRepository
import com.karthik.network.home.bottomliketab.models.UserLikedPhotoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

//TODO: cache the response.
class BottomLikeTabRepository(
    retrofit: Retrofit ,
    private val memoryCache: IMemoryCache,
    private val internetHandler: IInternetHandler
) : IBottomLikeTabRepository {

    private val bottomLikeTabNetworkService: BottomLikeTabNetworkService by lazy {
        retrofit.create(BottomLikeTabNetworkService::class.java)
    }

    override suspend fun getUserLikedPhotos(): UserLikedPhotoResponse {
        if (internetHandler.isInternetAvailable()) {
            return withContext(Dispatchers.IO) {
                getFreshUserProfile()
            }
        }
        return UserLikedPhotoResponse.UserLikedPhotoErrorState(UserOfflineException())
    }


    private suspend fun getFreshUserProfile(): UserLikedPhotoResponse {
        if (memoryCache.getUserName().isNullOrEmpty()) {
            val userProfileResponse = bottomLikeTabNetworkService.getUserProfile()

            if (!userProfileResponse.isSuccessful || userProfileResponse.body() == null) {
                return UserLikedPhotoResponse.UserLikedPhotoErrorState(IllegalStateException())
            }

            userProfileResponse.body()?.let { profile ->
                memoryCache.setUserName(profile.username)
            } ?: return UserLikedPhotoResponse.UserLikedPhotoErrorState(IllegalStateException())
        }

        memoryCache.getUserName()?.let {
            val likedPhotosResponse = bottomLikeTabNetworkService.getUserLikePhotos(it)
            if (!likedPhotosResponse.isSuccessful || likedPhotosResponse.body() == null) {
                return UserLikedPhotoResponse.UserLikedPhotoErrorState(IllegalStateException())
            }

            likedPhotosResponse.body()?.let { photos ->
                return UserLikedPhotoResponse.UserLikedPhoto(photos = photos)
            } ?: return UserLikedPhotoResponse.UserLikedPhotoErrorState(IllegalStateException())

        } ?: return UserLikedPhotoResponse.UserLikedPhotoErrorState(IllegalStateException())
    }
}
