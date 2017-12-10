package com.karthik.splash.RestServices.Services;

import com.karthik.splash.Models.PhotoDetail.PhotoDetailInfo;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by karthikrk on 09/12/17.
 */

public interface PhotoService {
    @GET("photos/{id}")
    Single<PhotoDetailInfo> getPhotoInfo(@Path("id")String id);


}
