package com.karthik.splash.homescreen.bottomliketab.network

import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.models.UserProfile.Profile
import com.karthik.splash.restserviceutility.UserOfflineException
import com.karthik.splash.storage.MemoryCache
import com.karthik.splash.storage.db.SplashDao
import com.karthik.splash.storage.db.entity.PhotosStorage
import com.karthik.splash.storage.db.entity.UserInfo
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.lang.IllegalStateException
import java.util.ArrayList

class BottomLikeTabRepository(retrofit: Retrofit, private val memoryCache: MemoryCache,
                              private val localdb: SplashDao,
                              private val internetHandler: InternetHandler) {
    private val bottomLikeTabNetworkService: BottomLikeTabNetworkService
    private val disposable = CompositeDisposable()
    private val LIKE  = "LIKE"

    init {
        bottomLikeTabNetworkService = retrofit.create(BottomLikeTabNetworkService::class.java)
    }

    fun getUserLikedPhotos(successhander:(ArrayList<Photos>)->Unit,
                           errorhandler:(e: Throwable)->Unit){
        if(memoryCache.getUserName()==null){
            getUserProfile(successhander,errorhandler)
            return
        }
        getLikedPhotos(successhander,errorhandler,memoryCache.getUserName()!!)
    }

    private fun getUserProfile(successhander:(ArrayList<Photos>)->Unit,
                               errorhandler:(e: Throwable)->Unit) {
        when {
            internetHandler.isInternetAvailable() ->  getFreshUserProfile(successhander,errorhandler)
            memoryCache.isCacheAvail() -> getUserProfileLocalDb(successhander, errorhandler)
            else -> errorhandler(UserOfflineException())
        }
    }

    private fun getFreshUserProfile(successhander:(ArrayList<Photos>)->Unit,
                                    errorhandler:(e: Throwable)->Unit){
        disposable.add(bottomLikeTabNetworkService.getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<Profile>(){
                    override fun onSuccess(profile: Profile) {
                        if(profile.username!=null) {
                            memoryCache.setUserName(profile.username!!)
                            localdb.setUserInfo(UserInfo(id = profile.id,username = profile.username,bio = profile.bio,
                                    email = profile.email,authcode = memoryCache.getAuthCode(),user = "${profile.id}:${profile.email}"))
                            getLikedPhotos(successhander, errorhandler, profile.username!!)
                            return
                        }
                        errorhandler(IllegalStateException("username is empty"))
                    }
                    override fun onError(e: Throwable) = errorhandler(e)
                }))
    }

    private fun getUserProfileLocalDb(successhander:(ArrayList<Photos>)->Unit,
                                      errorhandler:(e: Throwable)->Unit){
        disposable.add(localdb.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableMaybeObserver<UserInfo>(){
            override fun onSuccess(t: UserInfo) = getLikedPhotos(successhander, errorhandler,t.username!!)

            override fun onError(e: Throwable) = errorhandler(e)

            override fun onComplete() {}
        }))
    }

    private fun getLikedPhotos(successhander:(ArrayList<Photos>)->Unit,
                               errorhandler:(e: Throwable)->Unit,username:String){
        when{
            internetHandler.isInternetAvailable()->getFreshSetOfLikedPhotos(successhander,errorhandler,username)
            memoryCache.isCacheAvail()->getLikedPhotosFromDb(successhander,errorhandler)
            else-> errorhandler(UserOfflineException())
        }
    }

    private fun getFreshSetOfLikedPhotos(successhander:(ArrayList<Photos>)->Unit,
                                         errorhandler:(e: Throwable)->Unit,username:String) {
        disposable.add(bottomLikeTabNetworkService.getUserLikePhotos(username)
                .subscribeOn(Schedulers.io())
                .flatMap {photos->
                    localdb.setPhotos(PhotosStorage(pagenumber = 1,type = LIKE,photos = photos,pgtype = "1:LIKE"))
                }
                .flatMap { localdb.getPhotos(page = 1,type = LIKE).toSingle() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<PhotosStorage>(){
                    override fun onSuccess(photoStorage: PhotosStorage) = successhander(photoStorage.photos)
                    override fun onError(e: Throwable) = errorhandler(e)
                }))
    }

    private fun getLikedPhotosFromDb(successhander: (ArrayList<Photos>) -> Unit,
                                     errorhandler: (e: Throwable) -> Unit) {
        disposable.add(localdb.getPhotos(1,LIKE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object:DisposableMaybeObserver<PhotosStorage>(){
                    override fun onSuccess(storedphotos: PhotosStorage) = successhander(storedphotos.photos)

                    override fun onError(e: Throwable) = errorhandler(e)

                    override fun onComplete() {}
                }))
    }

    fun clearResources(){
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}