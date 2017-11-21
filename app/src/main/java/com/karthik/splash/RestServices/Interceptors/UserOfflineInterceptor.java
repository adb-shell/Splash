package com.karthik.splash.RestServices.Interceptors;

import android.content.Context;

import com.karthik.splash.RestServices.UserOfflineException;
import com.karthik.splash.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by karthikrk on 19/11/17.
 */

public class UserOfflineInterceptor implements Interceptor {
    private Context context;

    public UserOfflineInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(!Utils.isInternetAvailable(context)){
            throw new UserOfflineException();
        }
        return chain.proceed(chain.request());
    }
}
