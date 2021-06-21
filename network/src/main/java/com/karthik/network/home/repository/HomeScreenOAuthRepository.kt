package com.karthik.network.home.repository


import com.karthik.network.Constants
import com.karthik.network.home.IHomeScreenOAuthRepository
import com.karthik.network.home.models.HomeScreenLoginState
import com.karthik.network.home.models.OAuthBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalArgumentException

internal class HomeScreenOAuthRepository(
        okHttpClient: OkHttpClient
) : IHomeScreenOAuthRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.oauthBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

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
