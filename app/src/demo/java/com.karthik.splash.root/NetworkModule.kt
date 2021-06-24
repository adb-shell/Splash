package com.karthik.splash.root

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.karthik.network.interceptors.AuthorizationKeyInterceptor
import com.karthik.network.interceptors.UserOfflineInterceptor
import com.karthik.splash.BuildConfig
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Named("BASE_URL")
    @Provides
    fun providesBaseURL() = BuildConfig.SPLASH_BASE_URL

    @Provides
    @Singleton
    fun providesOkhttpClient(context:Context,
                             memoryCache: MemoryCache,
                             internetHandler: InternetHandler,
                             okhttpbuilder: OkHttpClient.Builder):OkHttpClient{
        val httpLogger = HttpLoggingInterceptor()
        okhttpbuilder.addInterceptor(UserOfflineInterceptor(internetHandler))
                .addInterceptor(AuthorizationKeyInterceptor(memoryCache))
                .addInterceptor(httpLogger)

        if(BuildConfig.DEBUG){
            httpLogger.level = HttpLoggingInterceptor.Level.BODY
            okhttpbuilder.addInterceptor(ChuckerInterceptor(context))
        }else{
            httpLogger.level = HttpLoggingInterceptor.Level.NONE
        }
        return okhttpbuilder.build()
    }
}
