package com.karthik.splash.homescreen.bottomliketab.network

import com.karthik.splash.models.photoslists.Photos
import com.karthik.splash.models.userprofile.Profile

import java.util.ArrayList

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by karthikrk on 30/11/17.
 */

interface BottomLikeTabNetworkService {

    @GET("me")
    suspend fun getUserProfile(): Response<Profile>

    @GET("users/{username}/likes")
    suspend fun getUserLikePhotos(@Path("username") username: String): Response<ArrayList<Photos>>
}
