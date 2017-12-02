package com.karthik.splash.Presenters;

import com.karthik.splash.BuildConfig;
import com.karthik.splash.Contracts.FeedsLikesContract;
import com.karthik.splash.Models.PhotosLists.Photos;
import com.karthik.splash.RestServices.NetworkLayer.UserServiceNetworkLayer;
import com.karthik.splash.Storage.Cache;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by karthikrk on 25/11/17.
 */

public class FeedsLikePresenter implements FeedsLikesContract.presenter{

    private FeedsLikesContract.view view;
    private Cache cache;
    private final String userScope = "public+read_user+read_photos+write_likes";
    private UserServiceNetworkLayer userServiceNetworkLayer;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public FeedsLikePresenter(FeedsLikesContract.view view, Cache cache,
                              UserServiceNetworkLayer networkLayer){
        this.view = view;
        this.cache = cache;
        this.userServiceNetworkLayer = networkLayer;
    }

    @Override
    public void decideScreenType() {
        if(cache.isUserLoggedIn()){
            disposable.add(getUsersLikeList());
            return;
        }
        view.hideProgress();
        view.showLoginScreen();
    }

    @Override
    public void login() {
        String url = BuildConfig.SPLASH_LOGIN_URL+"?client_id="+BuildConfig.SPLASH_KEY
                +"&redirect_uri="+BuildConfig.SPLASH_LOGIN_CALLBACK
                +"&response_type="+"code"
                +"&scope="+userScope;
        view.openLoginOauthUrl(url);
    }

    @Override
    public void clearResources() {
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    private Disposable getUsersLikeList() {
        return userServiceNetworkLayer.getUserLikedPhotos()
                .subscribeWith(new DisposableSingleObserver<List<Photos>>() {
                    @Override
                    public void onSuccess(List<Photos> photos) {
                        view.hideProgress();
                        view.showLikesList(photos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgress();
                        view.showEmptyLikedScreen();
                    }
                });
    }
}
