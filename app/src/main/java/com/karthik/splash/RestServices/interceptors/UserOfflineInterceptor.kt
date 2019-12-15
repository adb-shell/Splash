package com.karthik.splash.restservices.interceptors

import android.content.Context

import com.karthik.splash.restservices.UserOfflineException
import com.karthik.splash.misc.Utils

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by karthikrk on 19/11/17.
 */

class UserOfflineInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!Utils.isInternetAvailable(context)) {
            throw UserOfflineException()
        }
        return chain.proceed(chain.request())
    }
}
