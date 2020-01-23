package com.karthik.splash.homescreen.bottomliketab.network

import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.models.UserProfile.Profile

import java.util.ArrayList

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by karthikrk on 30/11/17.
 */

interface BottomLikeTabNetworkService {

    @GET("me")
    fun getUserProfile(): Single<Profile>

    @GET("users/{username}/likes")
    fun getUserLikePhotos(@Path("username") username: String): Single<ArrayList<Photos>>
}
