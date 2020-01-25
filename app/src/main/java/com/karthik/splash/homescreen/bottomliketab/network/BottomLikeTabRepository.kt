package com.karthik.splash.homescreen.bottomliketab.network

import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.models.UserProfile.Profile
import com.karthik.splash.storage.Cache
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.lang.IllegalStateException
import java.util.ArrayList

class BottomLikeTabRepository(retrofit: Retrofit, private val cache: Cache) {
    private val bottomLikeTabNetworkService: BottomLikeTabNetworkService
    private val disposable = CompositeDisposable()

    init {
        bottomLikeTabNetworkService = retrofit.create(BottomLikeTabNetworkService::class.java)
    }

    fun getUserLikedPhotos(successhander:(ArrayList<Photos>)->Unit,
                           errorhandler:(e: Throwable)->Unit){
        if(cache.getUserName()==null){
            getUserProfile(successhander,errorhandler)
            return
        }
        getLikedPhotos(successhander,errorhandler,cache.getUserName()!!)
    }

    private fun getUserProfile(successhander:(ArrayList<Photos>)->Unit,
                               errorhandler:(e: Throwable)->Unit) {
        disposable.add(bottomLikeTabNetworkService.getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<Profile>(){
            override fun onSuccess(profile: Profile) {
                if(profile.username!=null) {
                    cache.setUserName(profile.username!!)
                    getLikedPhotos(successhander, errorhandler, profile.username!!)
                    return
                }
                errorhandler(IllegalStateException("username is empty"))
            }
            override fun onError(e: Throwable) = errorhandler(e)
        }))
    }

    private fun getLikedPhotos(successhander:(ArrayList<Photos>)->Unit,
                               errorhandler:(e: Throwable)->Unit,username:String){
        disposable.add(bottomLikeTabNetworkService.getUserLikePhotos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<ArrayList<Photos>>(){
            override fun onSuccess(photos: ArrayList<Photos>) {
                successhander(photos)
            }
            override fun onError(e: Throwable) = errorhandler(e)
        }))
    }

    fun clearResources(){
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }


}