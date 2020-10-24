package com.karthik.splash.homescreen.network

import com.karthik.splash.models.oauth.OAuthBody
import com.karthik.splash.models.oauth.UserAuth
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomeScreenOAuthRepository(okHttpClient: OkHttpClient) : IHomeScreenOAuthRepository {
    private val retrofit:Retrofit
    private val OAUTH_BASE = "https://unsplash.com/"

    init {
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(OAUTH_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    override fun postOAuth(oAuthBody: OAuthBody): Single<UserAuth> =
            retrofit.create(HomeScreenOAuthService::class.java).OauthAuthorize(oAuthBody)
                    .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}