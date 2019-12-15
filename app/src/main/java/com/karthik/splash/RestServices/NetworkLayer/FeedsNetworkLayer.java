package com.karthik.splash.RestServices.NetworkLayer;

import com.karthik.splash.models.PhotosLists.Photos;
import com.karthik.splash.RestServices.Services.FeedsService;
import com.karthik.splash.Storage.Cache;
import com.karthik.splash.Storage.SqlLiteDbHandler;
import com.karthik.splash.misc.Utils;


import java.util.ArrayList;

import javax.inject.Inject;


import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;



import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;



/**
 * Created by karthikrk on 17/11/17.
 */

public class FeedsNetworkLayer {
    private FeedsService feedsNetworkService;
    private Cache localCache;
    private SqlLiteDbHandler dbHandler;

    @Inject
    public FeedsNetworkLayer(Retrofit retrofit, Cache localCache, SqlLiteDbHandler dbHandler){
        feedsNetworkService = retrofit.create(FeedsService.class);
        this.localCache = localCache;
        this.dbHandler = dbHandler;
    }

    public Single<ArrayList<Photos>> getNewFeedsCache(){
        return Single.just(dbHandler.getCachedHomeNewResponse());
    }

    public Single<ArrayList<Photos>> getTrendingFeedsCache(){
        return Single.just(dbHandler.getTrendingHomeNewResponse());
    }

    public Single<ArrayList<Photos>> getFeaturedFeedsCache(){
        return Single.just(dbHandler.getFeaturedHomeNewResponse());
    }


    public Single<ArrayList<Photos>> getNewFeeds(int pageNo){
        return feedsNetworkService.getPhotos("latest",pageNo)
                .subscribeOn(Schedulers.io())
                .map(photos -> {
                    localCache.setCacheAvail();
                    dbHandler.updateHomeNewResponseCache(
                            Utils.convertArrayListToString(photos));
                    return photos;
                })
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Single<ArrayList<Photos>> getTrendingFeeds(int pageNo){
        return feedsNetworkService.getPhotos("popular",pageNo)
                .subscribeOn(Schedulers.io())
                .map(photos -> {
                    localCache.setCacheAvail();
                    dbHandler.updateHomeTrendingResponseCache(
                            Utils.convertArrayListToString(photos));
                    return photos;
                })
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Single<ArrayList<Photos>> getFeaturedFeeds(int pageNo){
        return feedsNetworkService.getPhotos("oldest",pageNo)
                .subscribeOn(Schedulers.io())
                .map(photos -> {
                    localCache.setCacheAvail();
                    dbHandler.updateHomeFeaturedResponseCache(
                            Utils.convertArrayListToString(photos));
                    return photos;
                })
                .observeOn(AndroidSchedulers.mainThread());

    }
}
