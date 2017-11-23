package com.karthik.splash.RestServices.NetworkLayer;

import com.karthik.splash.Models.Photos;
import com.karthik.splash.RestServices.Services.FeedsService;
import com.karthik.splash.Storage.Cache;
import com.karthik.splash.Storage.SqlLiteDbHandler;
import com.karthik.splash.Utils;



import java.util.List;

import javax.inject.Inject;


import io.reactivex.Flowable;
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


    public Flowable<List<Photos>> getNewFeedsFromCacheAndNetwork(){
        return Single.concat(getNewFeeds(1), getNewFeedsCache())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<Photos>> getTrendingFeedsFromCacheAndNetwork(){
        return Single.concat(getTrendingFeeds(1),getTrendingFeedsCache())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<Photos>> getFeaturedFeedsFromCacheAndNetwork(){
        return Single.concat(getFeaturedFeeds(1),getFeaturedFeedsCache())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<List<Photos>> getNewFeeds(int pageNo){
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

    public Single<List<Photos>> getTrendingFeeds(int pageNo){
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

    public Single<List<Photos>> getFeaturedFeeds(int pageNo){
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

    private Single<List<Photos>> getNewFeedsCache(){
        return Single.just(dbHandler.getCachedHomeNewResponse());
    }

    private Single<List<Photos>> getTrendingFeedsCache(){
        return Single.just(dbHandler.getTrendingHomeNewResponse());
    }

    private Single<List<Photos>> getFeaturedFeedsCache(){
        return Single.just(dbHandler.getFeaturedHomeNewResponse());
    }
}
