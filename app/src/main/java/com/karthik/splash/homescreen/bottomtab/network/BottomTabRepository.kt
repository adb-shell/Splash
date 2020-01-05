package com.karthik.splash.homescreen.bottomtab.network

import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.storage.Cache
import com.karthik.splash.storage.SqlLiteDbHandler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.Retrofit
import java.util.ArrayList

class BottomTabRepository(retrofit: Retrofit,
                          private val cache:Cache,
                          private val dbHandler: SqlLiteDbHandler) {

    private val bottomTabNetworkService: BottomTabNetworkService
    private val disposable:CompositeDisposable

    companion object{
        const val SORT_BY_LATEST = "latest"
        const val SORT_BY_POPULAR = "popular"
        const val SORT_BY_OLDEST = "oldest"
    }

    init {
        bottomTabNetworkService = retrofit.create(BottomTabNetworkService::class.java)
        disposable = CompositeDisposable()
    }

    fun getNewFeedCache() = Single.just(dbHandler.getCachedHomeNewResponse())

    fun getTrendingFeedCache() = Single.just(dbHandler.getTrendingHomeNewResponse())

    fun getFeaturedFeedCache() = Single.just(dbHandler.getFeaturedHomeNewResponse())

    fun getFeeds(pageno:Int=1,
                    type:String,
                    successhander:(ArrayList<Photos>)->Unit,
                    errorhandler:(e: Throwable)->Unit) {
        disposable.add(bottomTabNetworkService.getPhotos(type, pageno)
                .map { photos ->
                    //TODO:cache implementation
                    /*cache.setCacheAvail()
                    dbHandler.updateHomeNewResponseCache(
                            Utils.convertArrayListToString(photos))*/
                    photos
                }.observeOn(AndroidSchedulers.mainThread())
                 .subscribeWith(object: DisposableSingleObserver<ArrayList<Photos>>(){
                    override fun onSuccess(photos: ArrayList<Photos>) = successhander(photos)
                    override fun onError(e: Throwable) = errorhandler(e)
                }))
    }

    fun clearResources(){
        if(!disposable.isDisposed)
            disposable.dispose()
    }
}