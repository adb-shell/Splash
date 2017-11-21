package com.karthik.splash.RestServices.Interceptors;

import com.karthik.splash.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by karthikrk on 20/11/17.
 */

public class APIKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl oldHttpurl = chain.request().url();

        HttpUrl newHttpurl = oldHttpurl.newBuilder()
                .addQueryParameter("client_id",BuildConfig.SPLASH_KEY)
                .build();

        Request newRequest = chain.request()
                .newBuilder()
                .url(newHttpurl)
                .build();

        return chain.proceed(newRequest);
    }
}
