package com.karthik.splash.restserviceutility.interceptors

import com.karthik.splash.BuildConfig
import com.karthik.splash.storage.MemoryCache

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by karthikrk on 29/11/17.
 */

class AuthorizationKeyInterceptor(private val memoryCache: MemoryCache) : Interceptor {
    private val Authorization = "Authorization"
    private val Bearer = "Bearer "
    private val ClientId = "client_id"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (memoryCache.isUserLoggedIn()) {
            val request = chain.request()
                    .newBuilder()
                    .addHeader(Authorization, Bearer + memoryCache.getAuthCode()!!)
                    .build()
            return chain.proceed(request)
        } else {
            val newHttpurl = chain.request().url().newBuilder()
                    .addQueryParameter(ClientId, BuildConfig.SPLASH_KEY)
                    .build()

            val newRequest = chain.request()
                    .newBuilder()
                    .url(newHttpurl)
                    .build()

            return chain.proceed(newRequest)
        }
    }
}
