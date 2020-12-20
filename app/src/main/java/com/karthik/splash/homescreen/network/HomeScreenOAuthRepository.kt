package com.karthik.splash.homescreen.network

import com.karthik.splash.homescreen.HomeScreenLoginState
import com.karthik.splash.models.oauth.OAuthBody
import com.karthik.splash.storage.MemoryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalArgumentException

class HomeScreenOAuthRepository(
        okHttpClient: OkHttpClient
) : IHomeScreenOAuthRepository {
    private val retrofit: Retrofit
    private val oauthBase = "https://unsplash.com/"

    init {
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(oauthBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    override suspend fun postOAuth(oAuthBody: OAuthBody): HomeScreenLoginState {
        return withContext(Dispatchers.IO) {
            try {
                val userAuthResponse = retrofit.create(HomeScreenOAuthService::class.java)
                        .oauthAuthorize(oAuthBody)
                if (userAuthResponse.isSuccessful) {
                    userAuthResponse.body()?.let {
                        HomeScreenLoginState.LoginSuccess(it)
                    } ?: HomeScreenLoginState.LoginFailed(IllegalArgumentException())
                } else {
                    HomeScreenLoginState.LoginFailed(IllegalArgumentException())
                }
            } catch (error: Throwable) {
                HomeScreenLoginState.LoginFailed(error)
            }
        }
    }
}
