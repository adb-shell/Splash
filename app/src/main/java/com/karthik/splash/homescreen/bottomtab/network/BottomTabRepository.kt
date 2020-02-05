package com.karthik.splash.homescreen.bottomtab.network

import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.restserviceutility.UserOfflineException
import com.karthik.splash.storage.MemoryCache
import com.karthik.splash.storage.db.SplashDao
import com.karthik.splash.storage.db.entity.PhotosStorage
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.Retrofit

class BottomTabRepository(retrofit: Retrofit,
                          private val localdb: SplashDao,
                          private val cache: MemoryCache,
                          private val internetHandler: InternetHandler) {

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

    fun getFeeds(pageno:Int=1,
                    type:String,
                    successhander:(ArrayList<Photos>)->Unit,
                    errorhandler:(e: Throwable)->Unit) {
        when {
            internetHandler.isInternetAvailable() -> getFreshFeeds(pageno,type,successhander,errorhandler)
            cache.isCacheAvail() -> getFeedsFromLocalDb(pageno,type,successhander,errorhandler)
            else -> errorhandler(UserOfflineException())
        }
    }

    private fun getFreshFeeds(pageno:Int=1,
                              type:String,
                              successhander:(ArrayList<Photos>)->Unit,
                              errorhandler:(e: Throwable)->Unit){
        disposable.add(bottomTabNetworkService.getPhotos(type, pageno)
                .flatMap {photos->
                    localdb.setPhotos(PhotosStorage(pagenumber = pageno,type = type,
                            photos = photos,pgtype = "$pageno:$type"))
                }.flatMap { localdb.getPhotos(page = pageno,type = type).toSingle() }
               .subscribeWith(object:DisposableSingleObserver<PhotosStorage>(){
                   override fun onSuccess(photostorage: PhotosStorage) {
                       //TODO:make it more specific
                       cache.setCacheAvail()
                       successhander(photostorage.photos)
                   }
                   override fun onError(e: Throwable) = errorhandler(e)
               }))
    }

    private fun getFeedsFromLocalDb(pageno:Int=1,
                                    type:String,
                                    successhander:(ArrayList<Photos>)->Unit,
                                    errorhandler:(e: Throwable)->Unit){
        disposable.add(localdb.getPhotos(pageno,type).subscribeWith(object:DisposableMaybeObserver<PhotosStorage>(){
            override fun onSuccess(storagephotos: PhotosStorage) = successhander(storagephotos.photos)
            override fun onError(e: Throwable) = errorhandler(e)
            override fun onComplete(){}
        }))
    }

    fun clearResources(){
        if(!disposable.isDisposed)
            disposable.dispose()
    }
}