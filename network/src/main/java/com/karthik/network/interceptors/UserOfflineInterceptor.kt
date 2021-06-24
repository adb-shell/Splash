package com.karthik.network.interceptors


import com.karthik.network.IInternetHandler
import com.karthik.network.UserOfflineException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by karthikrk on 19/11/17.
 */

class UserOfflineInterceptor(private val internetHandler: IInternetHandler) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!internetHandler.isInternetAvailable()) {
            throw UserOfflineException()
        }
        return chain.proceed(chain.request())
    }
}
