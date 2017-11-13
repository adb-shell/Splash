package com.karthik.splash.Modules;

import com.karthik.splash.Contracts.SplashContract;
import com.karthik.splash.Presenters.SplashPresenter;
import com.karthik.splash.Storage.Cache;

import dagger.Module;
import dagger.Provides;

/**
 * Created by karthikrk on 12/11/17.
 */
@Module
public class SplashScreenModule {

    private SplashContract.SplashView splashView;

    public SplashScreenModule(SplashContract.SplashView splashView){
        this.splashView = splashView;
    }

    @Provides
    public SplashContract.SplashView provideSplashView(){
        return splashView;
    }

    @Provides
    public SplashContract.SplashPresenter providesPresenter(
            SplashContract.SplashView splashView,Cache cache){
        return new SplashPresenter(splashView,cache);
    }
}
