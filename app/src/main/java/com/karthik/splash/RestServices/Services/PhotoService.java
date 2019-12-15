package com.karthik.splash.RestServices.Services;

import com.karthik.splash.models.likephoto.LikeResponse;
import com.karthik.splash.models.photodetail.PhotoDetailInfo;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by karthikrk on 09/12/17.
 */

public interface PhotoService {
    @GET("photos/{id}")
    Single<PhotoDetailInfo> getPhotoInfo(@Path("id")String id);

    @POST("photos/{id}/like")
    Single<LikeResponse> likePhoto(@Path("id") String id);

}
