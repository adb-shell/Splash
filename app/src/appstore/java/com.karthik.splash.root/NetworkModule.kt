package com.karthik.splash.root

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
