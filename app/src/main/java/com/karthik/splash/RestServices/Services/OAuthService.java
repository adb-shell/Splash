package com.karthik.splash.RestServices.Services;

import com.karthik.splash.Models.Oauth.OAuthBody;
import com.karthik.splash.Models.Oauth.UserAuth;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by karthikrk on 29/11/17.
 */

public interface OAuthService {
    @POST("oauth/token")
    Single<UserAuth> OauthAuthorize(@Body OAuthBody oAuthBody);
}
