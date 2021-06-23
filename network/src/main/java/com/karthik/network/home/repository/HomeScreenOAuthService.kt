package com.karthik.network.home.repository


import com.karthik.network.home.models.OAuthBody
import com.karthik.network.home.models.UserAuth
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by karthikrk on 29/11/17.
 */

internal interface HomeScreenOAuthService {
    @POST("oauth/token")
    suspend fun oauthAuthorize(@Body oAuthBody: OAuthBody): Response<UserAuth>
}
