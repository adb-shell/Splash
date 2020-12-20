package com.karthik.splash.homescreen.bottomliketab.network

import com.karthik.splash.homescreen.bottomliketab.UserLikedPhotoResponse
import com.karthik.splash.misc.IInternetHandler
import com.karthik.splash.restserviceutility.UserOfflineException
import com.karthik.splash.storage.IMemoryCache
import com.karthik.splash.storage.db.SplashDao
import com.karthik.splash.storage.db.entity.PhotosStorage
import com.karthik.splash.storage.db.entity.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

class BottomLikeTabRepository(
        private val bottomLikeTabNetworkService: BottomLikeTabNetworkService,
        private val memoryCache: IMemoryCache,
        private val localdb: SplashDao,
        private val internetHandler: IInternetHandler
) : IBottomLikeTabRepository {
    private val _like = "LIKE"


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
                //save data in local db
                localdb.setUserInfo(UserInfo(
                        id = profile.id,
                        username = profile.username,
                        bio = profile.bio,
                        email = profile.email,
                        authcode = memoryCache.getAuthCode(),
                        user = "${profile.id}:${profile.email}"))
            } ?: return UserLikedPhotoResponse.UserLikedPhotoErrorState(IllegalStateException())
        }

        memoryCache.getUserName()?.let {
            val likedPhotosResponse = bottomLikeTabNetworkService.getUserLikePhotos(it)
            if (!likedPhotosResponse.isSuccessful || likedPhotosResponse.body() == null) {
                return UserLikedPhotoResponse.UserLikedPhotoErrorState(IllegalStateException())
            }

            likedPhotosResponse.body()?.let { photos ->
                //save data in local db
                localdb.setPhotos(PhotosStorage(
                        pagenumber = 1,
                        type = _like,
                        photos = photos,
                        pgtype = "1:LIKE"))
                return UserLikedPhotoResponse.UserLikedPhoto(photos = photos)
            } ?: return UserLikedPhotoResponse.UserLikedPhotoErrorState(IllegalStateException())

        } ?: return UserLikedPhotoResponse.UserLikedPhotoErrorState(IllegalStateException())
    }
}
