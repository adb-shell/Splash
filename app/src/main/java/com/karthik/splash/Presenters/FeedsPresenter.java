package com.karthik.splash.Presenters;

import com.karthik.splash.homescreen.HomeScreenContract;
import com.karthik.splash.Models.Oauth.OAuthBody;
import com.karthik.splash.Models.Oauth.UserAuth;
import com.karthik.splash.R;
import com.karthik.splash.RestServices.NetworkLayer.OAuthNetworkLayer;
import com.karthik.splash.Storage.Cache;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by karthikrk on 14/11/17.
 */

public class FeedsPresenter implements HomeScreenContract.Presenter{

    private HomeScreenContract.View feedsView;
    private Cache cache;
    private OAuthNetworkLayer oAuthNetworkLayer;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public  FeedsPresenter(HomeScreenContract.View feedsView, Cache cache, OAuthNetworkLayer oAuthNetworkLayer){
        this.feedsView = feedsView;
        this.cache = cache;
        this.oAuthNetworkLayer = oAuthNetworkLayer;
    }

    @Override
    public void onNavigationItemSelected(int id) {
        if(feedsView.getSelectedItem()==id)
            return;

        switch (id){
            case R.id.navigation_home:
                feedsView.inflateHome();
                break;
            case R.id.navigation_likes:
                feedsView.inflateLikes();
                break;
            case R.id.navigation_settings:
                feedsView.inflateSettings();
                break;
        }
    }

    @Override
    public void getUserDetail(String code) {
        disposable.add(oAuthNetworkLayer.postOAuth(new OAuthBody(code)).subscribeWith(new DisposableSingleObserver<UserAuth>() {
            @Override
            public void onSuccess(UserAuth userAuth) {
                cache.setUserLoggedIn();
                cache.setAuthCode(userAuth.accessToken);
                feedsView.inflateLikes();
            }

            @Override
            public void onError(Throwable e) {
                feedsView.displayUnableToLogin();
                feedsView.inflateLikes();
            }
        }));
    }

    @Override
    public void clearResource() {
        if (disposable!=null  && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
