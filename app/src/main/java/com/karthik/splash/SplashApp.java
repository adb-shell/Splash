package com.karthik.splash;

import android.app.Application;

import com.karthik.splash.DI.DaggerSplashAppComponent;
import com.karthik.splash.DI.SplashAppComponent;
import com.karthik.splash.Modules.SplashAppModule;

/**
 * Created by karthikrk on 12/11/17.
 */

public class SplashApp extends Application{

    private SplashAppComponent splashAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        splashAppComponent = DaggerSplashAppComponent.builder()
                .splashAppModule(new SplashAppModule(this))
                .build();
        splashAppComponent.inject(this);
    }


    public SplashAppComponent getComponent(){
        return splashAppComponent;
    }
}
