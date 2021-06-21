package com.karthik.network.home

import com.karthik.network.home.models.HomeScreenLoginState
import com.karthik.network.home.models.OAuthBody

interface IHomeScreenOAuthRepository {
    suspend fun postOAuth(oAuthBody: OAuthBody): HomeScreenLoginState
}
