package com.karthik.splash.Modules;

import android.content.Context;

import com.karthik.splash.Contracts.HomeFeedsTabContract;
import com.karthik.splash.Presenters.HomeFeedsTabPresenter;
import com.karthik.splash.RestServices.NetworkLayer.FeedsNetworkLayer;
import com.karthik.splash.Storage.Cache;
import com.karthik.splash.Storage.SqlLiteDbHandler;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by karthikrk on 16/11/17.
 */
@Module
public class HomeTabFeedsModule {
    private HomeFeedsTabContract.View homeFeedsView;
    private SqlLiteDbHandler dbHandler;

    public HomeTabFeedsModule(HomeFeedsTabContract.View homeFeedsView,
                              Context context){
       this.homeFeedsView = homeFeedsView;
       dbHandler = new SqlLiteDbHandler(context);
    }

    @Provides
    public HomeFeedsTabContract.View providesHomeFeedsView(){
        return this.homeFeedsView;
    }

    @Provides
    public SqlLiteDbHandler providesDb(){
        return dbHandler;
    }


    @Provides
    public FeedsNetworkLayer providesFeedsNetworkLayer(Retrofit retrofit,
                                                       Cache cache,
                                                       SqlLiteDbHandler db){
        return new FeedsNetworkLayer(retrofit,cache,db);
    }

    @Provides
    public HomeFeedsTabContract.Presenter providesHomeFeedsPresenter(
            HomeFeedsTabContract.View view,
            FeedsNetworkLayer networkLayer){
        return new HomeFeedsTabPresenter(view,networkLayer);
    }
}
