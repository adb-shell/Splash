package com.karthik.splash.homescreen.network

import com.karthik.splash.homescreen.HomeScreenLoginState
import com.karthik.splash.models.oauth.OAuthBody

interface IHomeScreenOAuthRepository {
    suspend fun postOAuth(oAuthBody: OAuthBody): HomeScreenLoginState
}
