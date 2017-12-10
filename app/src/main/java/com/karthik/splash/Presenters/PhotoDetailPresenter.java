package com.karthik.splash.Presenters;

import com.karthik.splash.Contracts.PhotoDetailContract;
import com.karthik.splash.Models.PhotoDetail.PhotoDetailInfo;
import com.karthik.splash.RestServices.NetworkLayer.PhotoNetworkLayer;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by karthikrk on 09/12/17.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.PhotoDetailPresenter{
    private PhotoDetailContract.PhotoDetailView view;
    private PhotoNetworkLayer networkLayer;

    @Inject
    public PhotoDetailPresenter(PhotoDetailContract.PhotoDetailView
                                        view, PhotoNetworkLayer networkLayer){
        this.view = view;
        this.networkLayer = networkLayer;
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

    }

    @Override
    public void downloadPhoto(String url) {

    }
}
