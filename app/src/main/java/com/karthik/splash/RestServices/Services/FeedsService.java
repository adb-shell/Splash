package com.karthik.splash.RestServices.Services;




import com.karthik.splash.Models.Photos;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by karthikrk on 16/11/17.
 */

public interface FeedsService {

    @GET("photos")
    Single<List<Photos>> getNewPhotos(@Query("order_by")String order_by);
}
