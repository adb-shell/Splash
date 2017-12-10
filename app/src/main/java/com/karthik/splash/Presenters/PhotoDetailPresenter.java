package com.karthik.splash.Presenters;

import android.content.Context;

import com.esafirm.rxdownloader.RxDownloader;
import com.karthik.splash.Contracts.PhotoDetailContract;
import com.karthik.splash.Models.LikePhoto.LikeResponse;
import com.karthik.splash.Models.PhotoDetail.PhotoDetailInfo;
import com.karthik.splash.RestServices.NetworkLayer.PhotoNetworkLayer;
import com.karthik.splash.Storage.Cache;
import com.karthik.splash.Utils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by karthikrk on 09/12/17.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.PhotoDetailPresenter{
    private PhotoDetailContract.PhotoDetailView view;
    private PhotoNetworkLayer networkLayer;
    private Cache cache;

    @Inject
    public PhotoDetailPresenter(PhotoDetailContract.PhotoDetailView view,
                                PhotoNetworkLayer networkLayer,
                                Cache cache){
        this.view = view;
        this.networkLayer = networkLayer;
        this.cache = cache;
    }

    @Override
    public void getPhotoDetails(String id) {
        view.showLoading();
        networkLayer.getPhotoInfo(id)
                .subscribeWith(new DisposableSingleObserver<PhotoDetailInfo>() {
                    @Override
                    public void onSuccess(PhotoDetailInfo photoDetailInfo) {
                        view.hideLoading();
                        view.showPhotoDetails(photoDetailInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showDefaultView();
                    }
                });
    }

    @Override
    public void likeThePhoto(String id) {
        if(cache.isUserLoggedIn()){
            networkLayer.likePhoto(id)
                    .subscribeWith(new DisposableSingleObserver<LikeResponse>() {
                        @Override
                        public void onSuccess(LikeResponse likeResponse) {
                            view.successLikingPhoto();
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.errorLikingPhoto();
                        }
                    });
            return;
        }
        view.showLoginRequired();
    }

    @Override
    public void downloadPhoto(Context context,String fileName,String url) {
        if(view.isPermissionGranted()){
            RxDownloader.getInstance(context)
                    .download(url,fileName,Utils.PhotoMimeType)
                    .subscribe(path ->{
                        view.showFileDownloadedSuccessMessage(path);
                    },throwable -> {
                        view.showFileErrorDownloading();
            });
            return;
        }
        view.askPermission();
    }
}
