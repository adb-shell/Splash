package com.karthik.splash.RestServices.Interceptors;

import com.karthik.splash.BuildConfig;
import com.karthik.splash.Storage.Cache;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by karthikrk on 29/11/17.
 */

public class AuthorizationKeyInterceptor implements Interceptor{
    private Cache cache;
    private final String Authorization = "Authorization";
    private final String Bearer = "Bearer ";
    private final String ClientId = "client_id";

    public AuthorizationKeyInterceptor(Cache cache){
        this.cache = cache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(cache.isUserLoggedIn()){
            Request request  = chain.request()
                    .newBuilder()
                    .addHeader(Authorization,Bearer+cache.getAuthCode())
                    .build();
            return chain.proceed(request);
        }else{
           HttpUrl newHttpurl = chain.request().url().newBuilder()
                    .addQueryParameter(ClientId,BuildConfig.SPLASH_KEY)
                    .build();

            Request newRequest = chain.request()
                    .newBuilder()
                    .url(newHttpurl)
                    .build();

            return chain.proceed(newRequest);
        }
    }
}
