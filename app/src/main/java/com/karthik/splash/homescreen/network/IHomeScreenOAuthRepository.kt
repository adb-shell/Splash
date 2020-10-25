package com.karthik.splash.homescreen.network

import com.karthik.splash.models.oauth.OAuthBody
import com.karthik.splash.models.oauth.UserAuth
import io.reactivex.Single

interface IHomeScreenOAuthRepository {
    fun postOAuth(oAuthBody: OAuthBody): Single<UserAuth>
}