package com.karthik.splash.photodetailscreen

import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by karthikrk on 09/12/17.
 */

interface PhotoService {
    @GET("photos/{id}")
    fun getPhotoInfo(@Path("id") id: String): Single<PhotoDetailInfo>

    @POST("photos/{id}/like")
    fun likePhoto(@Path("id") id: String): Single<LikeResponse>

}
