package com.karthik.splash.homescreen.bottomtab


import com.karthik.splash.models.PhotosLists.Photos

import java.util.ArrayList

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by karthikrk on 16/11/17.
 */

interface BottomTabNetworkService {

    @GET("photos")
    fun getPhotos(@Query("order_by") order_by: String, @Query("page") pageNo: Int): Single<ArrayList<Photos>>
}
