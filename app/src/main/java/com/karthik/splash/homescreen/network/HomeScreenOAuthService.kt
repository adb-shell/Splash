package com.karthik.splash.homescreen.network

import com.karthik.splash.models.oauth.OAuthBody
import com.karthik.splash.models.oauth.UserAuth
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by karthikrk on 29/11/17.
 */

interface HomeScreenOAuthService {
    @POST("oauth/token")
    suspend fun oauthAuthorize(@Body oAuthBody: OAuthBody): Response<UserAuth>
}
