package com.karthik.splash.homescreen.bottomtab.network


import com.karthik.splash.models.photoslists.Photos

import java.util.ArrayList

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by karthikrk on 16/11/17.
 */

interface BottomTabNetworkService {

    @GET("photos")
    fun getPhotos(
            @Query("order_by") orderby: String,
            @Query("page") pageNo: Int
    ): Single<ArrayList<Photos>>
}
