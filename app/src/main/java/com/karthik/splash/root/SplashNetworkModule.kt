package com.karthik.splash.root

import android.content.Context
import com.karthik.splash.BuildConfig
import com.karthik.splash.restserviceutility.interceptors.AuthorizationKeyInterceptor
import com.karthik.splash.restserviceutility.interceptors.UserOfflineInterceptor
import com.karthik.splash.storage.Cache
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class SplashNetworkModule {

    private val TIME_OUT = 30L

    @Named("BASE_URL")
    @Provides
    fun providesBaseURL() = BuildConfig.SPLASH_BASE_URL

    @Provides
    @Singleton
    fun providesOkhttpClient(context:Context,cache: Cache):OkHttpClient{
        val httpLogger = HttpLoggingInterceptor()
        val okhttpbuilder = OkHttpClient.Builder()
                .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                .addInterceptor(UserOfflineInterceptor(context))
                .addInterceptor(AuthorizationKeyInterceptor(cache))
                .addInterceptor(httpLogger)

        if(BuildConfig.DEBUG){
            httpLogger.level = HttpLoggingInterceptor.Level.BODY
            okhttpbuilder.addInterceptor(ChuckInterceptor(context))
        }else{
            httpLogger.level = HttpLoggingInterceptor.Level.NONE
        }
        return okhttpbuilder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(@Named("BASE_URL") baseurl:String,okHttpClient: OkHttpClient):Retrofit =
            Retrofit.Builder().client(okHttpClient).baseUrl(baseurl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .build()
}