package com.karthik.splash.restservices.services;




import com.karthik.splash.models.PhotosLists.Photos;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by karthikrk on 16/11/17.
 */

public interface FeedsService {

    @GET("photos")
    Single<ArrayList<Photos>> getPhotos(@Query("order_by")String order_by, @Query("page") int pageNo);
}
