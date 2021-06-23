package com.karthik.network.photodetailscreen.repository

import com.karthik.network.photodetailscreen.models.LikeResponse
import com.karthik.network.photodetailscreen.models.PhotoDetailInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by karthikrk on 09/12/17.
 */

internal interface PhotoDetailsNetworkService {
    @GET("photos/{id}")
    suspend fun getPhotoInfo(@Path("id") id: String): Response<PhotoDetailInfo>

    @POST("photos/{id}/like")
    suspend fun likePhoto(@Path("id") id: String): Response<LikeResponse>

}
