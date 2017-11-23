package com.karthik.splash.Presenters;

import com.karthik.splash.Contracts.HomeFeedsTabContract;
import com.karthik.splash.Models.Photos;
import com.karthik.splash.RestServices.NetworkLayer.FeedsNetworkLayer;
import com.karthik.splash.RestServices.UserOfflineException;
import com.karthik.splash.Utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by karthikrk on 16/11/17.
 */

public class HomeFeedsTabPresenter implements HomeFeedsTabContract.Presenter{
    private HomeFeedsTabContract.View view;
    private FeedsNetworkLayer networkLayer;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final int MAX_LIMIT =3;

    @Inject
    public HomeFeedsTabPresenter(HomeFeedsTabContract.View view,
                                 FeedsNetworkLayer networkLayer){
        this.view = view;
        this.networkLayer = networkLayer;
    }

    @Override
    public void getFeeds(int mode,boolean isCacheAvailable,int pageSize) {
        switch (mode){
            case 0:
                getNewFeeds(isCacheAvailable,pageSize);
                break;
            case 1:
                getTrendingFeeds(isCacheAvailable,pageSize);
                break;
            case 2:
                getFeaturedFeeds(isCacheAvailable,pageSize);
                break;
            default:
                getNewFeeds(isCacheAvailable,pageSize);
        }
    }

    @Override
    public void getPaginatedFeeds(int mode, int pageSize) {
        getFeeds(mode,false,pageSize);
    }

    @Override
    public void clearResources() {
        if(compositeDisposable.isDisposed())
            return;
        compositeDisposable.dispose();
    }

    @Override
    public void manageErrors(Throwable e) {
        view.hideProgressBar();
        if(Utils.getErrorType(e)==Utils.NetworkErrorType.OFFLINE){
            view.showNoInternetScreen();
            return;
        }
        view.showEmptyScreen();
    }

    @Override
    public void managePhotos(List<Photos> photos) {
        view.hideProgressBar();
        if(photos==null && photos.isEmpty()) {
            view.showEmptyScreen();
            return;
        }
        view.showPhotosList(photos);
    }

    @Override
    public boolean isPaginatedItems() {
        return view.isFeedListVisible();
    }

    @Override
    public int getPageMaxLimit() {
        return MAX_LIMIT;
    }


    private void getFeaturedFeeds(boolean isCacheAvailable,int pageNo) {
        if(isCacheAvailable){
            compositeDisposable.add(getFeaturedFeedsFromCacheFirst());
            return;
        }
        compositeDisposable.add(getFeaturedFeedsFromNetworkFirst(pageNo));
    }

    private void getTrendingFeeds(boolean isCacheAvailable,int pageNo) {
        if(isCacheAvailable){
            compositeDisposable.add(getTrendingFeedsFromCacheFirst());
            return;
        }
        compositeDisposable.add(getTrendingFeedsFromNetworkFirst(pageNo));
    }

    private void getNewFeeds(boolean isCacheAvailable,int pageNo) {
        if(isCacheAvailable){
            compositeDisposable.add(getNewFeedsFromCacheFirst());
            return;
        }
        compositeDisposable.add(getNewFeedsFromNetworkFirst(pageNo));
    }



    private Disposable getNewFeedsFromCacheFirst() {
        return networkLayer.getNewFeedsFromCacheAndNetwork()
                .subscribeWith(new DisposableSubscriber<List<Photos>>() {
                    @Override
                    public void onNext(List<Photos> photos) {
                        managePhotos(photos);
                    }

                    @Override
                    public void onError(Throwable error) {
                        manageErrors(error);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Disposable getTrendingFeedsFromCacheFirst() {
        return networkLayer.getTrendingFeedsFromCacheAndNetwork()
                .subscribeWith(new DisposableSubscriber<List<Photos>>() {
                    @Override
                    public void onNext(List<Photos> photos) {
                        managePhotos(photos);
                    }

                    @Override
                    public void onError(Throwable error) {
                        manageErrors(error);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Disposable getFeaturedFeedsFromCacheFirst() {
        return networkLayer.getFeaturedFeedsFromCacheAndNetwork()
                .subscribeWith(new DisposableSubscriber<List<Photos>>() {
                    @Override
                    public void onNext(List<Photos> photos) {
                        managePhotos(photos);
                    }

                    @Override
                    public void onError(Throwable error) {
                        manageErrors(error);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    private Disposable getNewFeedsFromNetworkFirst(int pageNo) {
        return networkLayer.getNewFeeds(pageNo)
                .subscribeWith(new DisposableSingleObserver<List<Photos>>() {
                    @Override
                    public void onSuccess(List<Photos> photos) {
                        managePhotos(photos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        manageErrors(e);
                    }
                });
    }

    private Disposable getTrendingFeedsFromNetworkFirst(int pageNo) {
        return networkLayer.getTrendingFeeds(pageNo).subscribeWith(
                new DisposableSingleObserver<List<Photos>>() {
            @Override
            public void onSuccess(List<Photos> photos) {
                managePhotos(photos);
            }

            @Override
            public void onError(Throwable e) {
                manageErrors(e);
            }
        });
    }

    private Disposable getFeaturedFeedsFromNetworkFirst(int pageNo) {
        return networkLayer.getFeaturedFeeds(pageNo).subscribeWith(
                new DisposableSingleObserver<List<Photos>>() {
            @Override
            public void onSuccess(List<Photos> photos) {
                managePhotos(photos);
            }

            @Override
            public void onError(Throwable e) {
                manageErrors(e);
            }
        });
    }
}
