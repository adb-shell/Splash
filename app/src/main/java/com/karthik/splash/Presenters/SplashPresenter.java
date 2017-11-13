package com.karthik.splash.Presenters;

import com.karthik.splash.Contracts.SplashContract;
import com.karthik.splash.Storage.Cache;
import com.karthik.splash.Utils;

/**
 * Created by karthikrk on 12/11/17.
 */

public class SplashPresenter implements SplashContract.SplashPresenter{
    private SplashContract.SplashView splashView;
    private Cache cache;

    public SplashPresenter(SplashContract.SplashView splashView,Cache cache){
        this.splashView = splashView;
        this.cache = cache;
    }

    @Override
    public void decideScreens() {
        if(!Utils.isInternetAvailable(splashView.getSplashContext())
                && !cache.isCacheAvail()){
            splashView.showNoInternetScreen();
            return;
        }
        splashView.showDashBoardScreen(shouldShowCache());
        splashView.closeScreen();
    }

    @Override
    public boolean shouldShowCache() {
        return !Utils.isInternetAvailable(splashView.getSplashContext())
                && cache.isCacheAvail();
    }


}
