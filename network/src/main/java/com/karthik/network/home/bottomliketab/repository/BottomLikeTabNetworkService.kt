package com.karthik.network.home.bottomliketab.repository


import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomliketab.models.Profile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

/**
 * Created by karthikrk on 30/11/17.
 */

internal interface BottomLikeTabNetworkService {

    @GET("me")
    suspend fun getUserProfile(): Response<Profile>

    @GET("users/{username}/likes")
    suspend fun getUserLikePhotos(@Path("username") username: String): Response<ArrayList<Photos>>
}
