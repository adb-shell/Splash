package com.karthik.splash.restserviceutility.interceptors


import com.karthik.splash.misc.InternetHandler

import com.karthik.splash.restserviceutility.UserOfflineException

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by karthikrk on 19/11/17.
 */

class UserOfflineInterceptor(private val internetHandler: InternetHandler) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!internetHandler.isInternetAvailable()) {
            throw UserOfflineException()
        }
        return chain.proceed(chain.request())
    }
}
