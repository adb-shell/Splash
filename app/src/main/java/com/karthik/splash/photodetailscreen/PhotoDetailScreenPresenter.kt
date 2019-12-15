package com.karthik.splash.photodetailscreen

import android.content.Context
import com.esafirm.rxdownloader.RxDownloader
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import com.karthik.splash.restservices.networklayer.PhotoNetworkLayer
import com.karthik.splash.storage.Cache
import com.karthik.splash.misc.Utils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

class PhotoDetailScreenPresenter(private val view:PhotoDetailScreenContract.view,
                                 private val photoNetworkLayer: PhotoNetworkLayer,
                                 private val cache: Cache):PhotoDetailScreenContract.presenter {


    private val disposable = CompositeDisposable()

    override fun getPhotoDetails(id: String) {
        view.showLoading()
        disposable.add(photoNetworkLayer.getPhotoInfo(id).subscribeWith(object:DisposableSingleObserver<PhotoDetailInfo>(){
            override fun onSuccess(t: PhotoDetailInfo) {
                view.hideLoading()
                view.showPhotoDetails(t)
            }

            override fun onError(e: Throwable) {
                view.hideLoading()
                view.showDefaultView()
            }
        }))
    }

    override fun likeThePhoto(id: String) {
        if(cache.isUserLoggedIn()){
            view.showLoading()
            disposable.add(photoNetworkLayer.likePhoto(id).subscribeWith(object:DisposableSingleObserver<LikeResponse>(){
                override fun onSuccess(t: LikeResponse) {
                    view.hideLoading()
                    view.successLikingPhoto()
                }

                override fun onError(e: Throwable) {
                    view.hideLoading()
                    view.errorLikingPhoto()
                }
            }))
            return
        }
        view.showLoginRequired()
    }

    override fun downloadPhoto(context: Context, fileName: String, url: String?) {
        if(view.isPermissionGranted()){
            RxDownloader.getInstance(context)
                    .download(url, fileName, Utils.PhotoMimeType)
                    .subscribe({ path -> view.showFileDownloadedSuccessMessage(path) },
                               { view.showFileErrorDownloading() })
            return
        }
        view.askPermission()
    }

    override fun clearResources() {
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}