package com.karthik.network.interceptors

import com.karthik.network.Constants
import com.karthik.network.IMemoryCache
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by karthikrk on 29/11/17.
 */

class AuthorizationKeyInterceptor(private val memoryCache: IMemoryCache) : Interceptor {
    private val authorization = "Authorization"
    private val bearer = "Bearer "
    private val clientId = "client_id"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (memoryCache.isUserLoggedIn()) {
            val request = chain.request()
                    .newBuilder()
                    .addHeader(authorization, bearer + memoryCache.getAuthCode()!!)
                    .build()
            return chain.proceed(request)
        } else {
            val newHttpurl = chain.request().url().newBuilder()
                    .addQueryParameter(clientId, Constants.SPLASH_KEY)
                    .build()

            val newRequest = chain.request()
                    .newBuilder()
                    .url(newHttpurl)
                    .build()

            return chain.proceed(newRequest)
        }
    }
}
