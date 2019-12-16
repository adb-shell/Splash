package com.karthik.splash.homescreen.bottomtab

import com.karthik.splash.models.PhotosLists.Photos
import com.karthik.splash.misc.Utils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

class BottomTabPresenter(private val view:BottomTabContract.View,
                         private val bottomTabNetworkLayer: BottomTabNetworkLayer):BottomTabContract.Presenter {

    private val MAX_LIMIT = 3
    private val disposable = CompositeDisposable()

    override fun isPaginatedItems(): Boolean = view.isFeedListVisible()

    override fun getPageMaxLimit(): Int = MAX_LIMIT

    override fun getFeeds(mode: BottomTabTypes?, isFromCache: Boolean?, pageSize: Int) {
        if(mode==null||isFromCache==null)
            return

        when(mode){
            is BottomTabTypes.New-> getNewFeeds(isFromCache, pageSize)
            is BottomTabTypes.Featured-> getFeaturedFeeds(isFromCache, pageSize)
            is BottomTabTypes.Trending-> getTrendingFeeds(isFromCache, pageSize)
        }
    }

    override fun getPaginatedFeeds(mode: BottomTabTypes?, pageSize: Int) = getFeeds(mode, false, pageSize)

    override fun clearResources() {
        if(!disposable.isDisposed)
            disposable.dispose()
    }

    override fun manageErrors(e: Throwable) {
        view.hideProgressBar()
        if(Utils.getErrorType(e)== Utils.NetworkErrorType.OFFLINE){
            view.showNoInternetScreen()
            return
        }
        view.showEmptyScreen()
    }

    override fun managePhotos(photos: ArrayList<Photos>) {
        view.hideProgressBar()
        if(photos.isEmpty()){
            view.showEmptyScreen()
            return
        }
        view.showPhotosList(photos)
    }

    private fun getNewFeeds(isCacheAvailable: Boolean, pageSize: Int) {
        val photoscallback = object:DisposableSingleObserver<ArrayList<Photos>>(){
            override fun onSuccess(photos: ArrayList<Photos>) {
                managePhotos(photos)
            }

            override fun onError(e: Throwable) {
                manageErrors(e)
            }
        }

        if (isCacheAvailable) {
            disposable.add(bottomTabNetworkLayer.getNewFeedCache().subscribeWith(photoscallback))
            return
        }
        disposable.add(bottomTabNetworkLayer.getNewFeeds(pageSize).subscribeWith(photoscallback))
    }

    private fun getFeaturedFeeds(isCacheAvailable: Boolean, pageSize: Int) {
        val photoscallback = object:DisposableSingleObserver<ArrayList<Photos>>(){
            override fun onSuccess(photos: ArrayList<Photos>) {
                managePhotos(photos)
            }

            override fun onError(e: Throwable) {
                manageErrors(e)
            }
        }
        if(isCacheAvailable){
            disposable.add(bottomTabNetworkLayer.getFeaturedFeedCache().subscribeWith(photoscallback))
            return
        }
        disposable.add(bottomTabNetworkLayer.getFeaturedFeeds(pageSize).subscribeWith(photoscallback))
    }

    private fun getTrendingFeeds(isCacheAvailable: Boolean, pageSize: Int) {
        val photoscallback = object:DisposableSingleObserver<ArrayList<Photos>>(){
            override fun onSuccess(photos: ArrayList<Photos>) {
                managePhotos(photos)
            }

            override fun onError(e: Throwable) {
                manageErrors(e)
            }
        }
        if(isCacheAvailable){
            disposable.add(bottomTabNetworkLayer.getTrendingFeedCache().subscribeWith(photoscallback))
            return
        }
        disposable.add(bottomTabNetworkLayer.getTrendingFeeds(pageSize).subscribeWith(photoscallback))
    }
}