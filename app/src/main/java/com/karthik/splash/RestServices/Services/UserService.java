package com.karthik.splash.restservices.services;

import com.karthik.splash.models.PhotosLists.Photos;
import com.karthik.splash.models.UserProfile.Profile;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by karthikrk on 30/11/17.
 */

public interface UserService {

    @GET("me")
    Single<Profile> getUserProfile();

    @GET("users/{username}/likes")
    Single<ArrayList<Photos>> getUserLikePhotos(@Path("username")String username);
}
