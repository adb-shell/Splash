package com.karthik.splash.homescreen.bottomtab

import com.karthik.splash.misc.Utils
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.storage.Cache
import com.karthik.splash.storage.SqlLiteDbHandler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.ArrayList

class BottomTabNetworkLayer(retrofit: Retrofit,
                            private val cache:Cache,
                            private val dbHandler: SqlLiteDbHandler) {

    private val bottomTabNetworkService:BottomTabNetworkService

    private val SORT_BY_LATEST = "latest"
    private val SORT_BY_POPULAR = "popular"
    private val SORT_BY_OLDEST = "oldest"

    init {
        bottomTabNetworkService = retrofit.create(BottomTabNetworkService::class.java)
    }

    fun getNewFeedCache() = Single.just(dbHandler.getCachedHomeNewResponse())

    fun getTrendingFeedCache() = Single.just(dbHandler.getTrendingHomeNewResponse())

    fun getFeaturedFeedCache() = Single.just(dbHandler.getFeaturedHomeNewResponse())

    fun getNewFeeds(pageno:Int):Single<ArrayList<Photos>> =
            bottomTabNetworkService.getPhotos(SORT_BY_LATEST, pageno)
            .map { photos ->
                cache.setCacheAvail()
                dbHandler.updateHomeNewResponseCache(
                        Utils.convertArrayListToString(photos))
                photos
            }
            .observeOn(AndroidSchedulers.mainThread())

    fun getTrendingFeeds(pageno: Int):Single<ArrayList<Photos>> =
            bottomTabNetworkService.getPhotos(SORT_BY_POPULAR, pageno)
            .map { photos ->
                cache.setCacheAvail()
                dbHandler.updateHomeTrendingResponseCache(
                        Utils.convertArrayListToString(photos))
                photos
            }
            .observeOn(AndroidSchedulers.mainThread())

    fun getFeaturedFeeds(pageno:Int):Single<ArrayList<Photos>> =
            bottomTabNetworkService.getPhotos(SORT_BY_OLDEST, pageno)
            .subscribeOn(Schedulers.io())
            .map { photos ->
                cache.setCacheAvail()
                dbHandler.updateHomeFeaturedResponseCache(
                        Utils.convertArrayListToString(photos))
                photos
            }
            .observeOn(AndroidSchedulers.mainThread())
}