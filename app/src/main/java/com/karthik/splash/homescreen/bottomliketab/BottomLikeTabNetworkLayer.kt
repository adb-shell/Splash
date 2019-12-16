package com.karthik.splash.homescreen.bottomliketab

import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.storage.Cache
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import java.util.ArrayList

class BottomLikeTabNetworkLayer(retrofit: Retrofit,private val cache: Cache) {
    private val bottomLikeTabService: BottomLikeTabService
    init {
        bottomLikeTabService = retrofit.create(BottomLikeTabService::class.java)
    }

    fun getUserLikedPhotos(): Single<ArrayList<Photos>> {
        return if (cache.getUserName() != null) {
            getPhotos(cache.getUserName())
        } else {
            getUserProfileAndLikedPhotos()
        }
    }

    private fun getPhotos(username: String?): Single<ArrayList<Photos>> {
        return bottomLikeTabService.getUserLikePhotos(username!!)
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getUserProfileAndLikedPhotos(): Single<ArrayList<Photos>> {
        return bottomLikeTabService.getUserProfile()
                .flatMap { (_, _, username) ->
                    cache.setUserName(username!!)
                    getPhotos(username)
                }
                .observeOn(AndroidSchedulers.mainThread())
    }
}