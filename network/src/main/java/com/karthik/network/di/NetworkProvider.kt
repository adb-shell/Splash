package com.karthik.network.di

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
class NetworkProvider {

    private val TIME_OUT = 30L

    @Provides
    @Singleton
    fun providesOkhttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(@Named("BASE_URL") baseurl:String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().client(okHttpClient).baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}