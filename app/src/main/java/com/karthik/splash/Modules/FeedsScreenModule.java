package com.karthik.splash.Modules;

import com.karthik.splash.Contracts.FeedsContract;
import com.karthik.splash.Presenters.FeedsPresenter;
import com.karthik.splash.RestServices.NetworkLayer.OAuthNetworkLayer;
import com.karthik.splash.Storage.Cache;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by karthikrk on 14/11/17.
 */
@Module
public class FeedsScreenModule {
    private FeedsContract.View feedsView;

    public FeedsScreenModule(FeedsContract.View feedsView){
        this.feedsView = feedsView;
    }

    @Provides
    public FeedsContract.View providesFeedsView(){
        return feedsView;
    }

    @Provides
    OAuthNetworkLayer providesOAuthNetworkLayer(OkHttpClient okHttpClient,Cache cache){
        return new OAuthNetworkLayer(okHttpClient,cache);
    }

    @Provides
    FeedsContract.Presenter providesPresenter(FeedsContract.View feedsView,Cache cache,
                                              OAuthNetworkLayer networkLayer){
        return new FeedsPresenter(feedsView,cache,networkLayer);
    }
}
