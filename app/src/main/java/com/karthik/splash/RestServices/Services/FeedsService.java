package com.karthik.splash.RestServices.Services;




import com.karthik.splash.Models.PhotosLists.Photos;

import java.util.ArrayList;
import java.util.List;

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
