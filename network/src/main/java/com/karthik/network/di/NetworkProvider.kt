package com.karthik.network.di

import com.karthik.network.Constants
import com.karthik.network.ServiceProvider
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

    @Provides
    @Singleton
    fun providesOkhttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.timeOut, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(
        @Named("BASE_URL") baseurl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().client(okHttpClient).baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesNetworkService(retrofit: Retrofit) = ServiceProvider(retrofit = retrofit)
}