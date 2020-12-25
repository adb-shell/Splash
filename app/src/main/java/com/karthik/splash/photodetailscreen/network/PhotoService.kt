package com.karthik.splash.photodetailscreen.network

import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by karthikrk on 09/12/17.
 */

interface PhotoService {
    @GET("photos/{id}")
    suspend fun getPhotoInfo(@Path("id") id: String): Response<PhotoDetailInfo>

    @POST("photos/{id}/like")
    suspend fun likePhoto(@Path("id") id: String): Response<LikeResponse>

}
