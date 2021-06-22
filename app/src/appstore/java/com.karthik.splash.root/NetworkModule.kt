package com.karthik.splash.root

import android.content.Context
import com.karthik.splash.BuildConfig
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.restserviceutility.interceptors.AuthorizationKeyInterceptor
import com.karthik.splash.restserviceutility.interceptors.UserOfflineInterceptor
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    private val TIME_OUT = 30L

    @Named("BASE_URL")
    @Provides
    fun providesBaseURL() = BuildConfig.SPLASH_BASE_URL

    @Provides
    @Singleton
    fun providesOkhttpClient(cache: MemoryCache,
                             internetHandler: InternetHandler,
                             okhttpbuilder: OkHttpClient.Builder):OkHttpClient{
        val httpLogger = HttpLoggingInterceptor()
        okhttpbuilder.addInterceptor(UserOfflineInterceptor(internetHandler))
                     .addInterceptor(AuthorizationKeyInterceptor(cache))
                     .addInterceptor(httpLogger)

        httpLogger.level = HttpLoggingInterceptor.Level.NONE
        return okhttpbuilder.build()
    }
}
