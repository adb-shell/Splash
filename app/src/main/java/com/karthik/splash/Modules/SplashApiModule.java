package com.karthik.splash.Modules;

import android.content.Context;

import com.karthik.splash.BuildConfig;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;


import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karthikrk on 12/11/17.
 */
@Module
public class SplashApiModule {
    private final int TIME_OUT = 30;

    @Named("BASE_URL")
    @Provides
    public String providesAPIBaseUrl(){
        return BuildConfig.SPLASH_BASE_URL;
    }

    @Singleton
    @Provides
    public OkHttpClient providesOkhttpClient(Context context){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG)
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS);

        if(BuildConfig.DEBUG){
            builder.addInterceptor(new ChuckInterceptor(context));
        }

        return builder.build();
    }

    @Singleton
    @Provides
    public Retrofit providesRetrofit(@Named("BASE_URL") String baseUrl,
                                     OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
