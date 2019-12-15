package com.karthik.splash.restservices.networklayer;

import com.karthik.splash.models.oauth.OAuthBody;
import com.karthik.splash.models.oauth.UserAuth;
import com.karthik.splash.restservices.services.OAuthService;
import com.karthik.splash.storage.Cache;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karthikrk on 29/11/17.
 */

public class OAuthNetworkLayer {
    private Retrofit retrofit;
    private Cache cache;
    private final String OAUTH_BASE = "https://unsplash.com/";

    @Inject
    public OAuthNetworkLayer(OkHttpClient okHttpClient,Cache cache){
        this.cache = cache;
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(OAUTH_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Single<UserAuth> postOAuth(OAuthBody oAuthBody){
       return retrofit.create(OAuthService.class).OauthAuthorize(oAuthBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
