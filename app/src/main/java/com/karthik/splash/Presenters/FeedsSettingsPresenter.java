package com.karthik.splash.Presenters;

import com.karthik.splash.Contracts.FeedsSettingsContract;
import com.karthik.splash.Storage.Cache;

import javax.inject.Inject;

/**
 * Created by karthikrk on 03/12/17.
 */

public class FeedsSettingsPresenter implements FeedsSettingsContract.FeedsSettingsPresenter{
    private FeedsSettingsContract.FeedsSettingsView view;
    private Cache cache;


    @Inject
    public FeedsSettingsPresenter(FeedsSettingsContract.FeedsSettingsView view,Cache cache){
        this.view = view;
        this.cache = cache;
    }

    @Override
    public void decideScreen() {
        if(cache.isUserLoggedIn())
            view.showLoggedInView(cache.getUserName());
        else
            view.showNonLoggedInView();
    }

    @Override
    public void logOutUser() {
        cache.logOutUser();
        decideScreen();
    }

    @Override
    public void showDownloadedImages() {
        if(view.isReadPermissionGranted()){
           view.openFolder();
           return;
        }
        view.askReadPermission();
    }
}
