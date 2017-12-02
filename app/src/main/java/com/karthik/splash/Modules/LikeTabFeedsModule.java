package com.karthik.splash.Modules;

import com.karthik.splash.Contracts.FeedsLikesContract;
import com.karthik.splash.Presenters.FeedsLikePresenter;
import com.karthik.splash.RestServices.NetworkLayer.UserServiceNetworkLayer;
import com.karthik.splash.Storage.Cache;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by karthikrk on 25/11/17.
 */
@Module
public class LikeTabFeedsModule {
    private FeedsLikesContract.view view;

    public LikeTabFeedsModule(FeedsLikesContract.view view){
        this.view = view;
    }

    @Provides
    public FeedsLikesContract.view providesView(){
        return view;
    }


    @Provides
    public UserServiceNetworkLayer providesUserServiceNetwork(Retrofit retrofit,Cache cache){
        return new UserServiceNetworkLayer(retrofit,cache);
    }

    @Provides
    public FeedsLikesContract.presenter providesPresenter(
            FeedsLikesContract.view view,Cache cache,
            UserServiceNetworkLayer userServiceNetworkLayer){
        return new FeedsLikePresenter(view,cache,userServiceNetworkLayer);
    }
}
