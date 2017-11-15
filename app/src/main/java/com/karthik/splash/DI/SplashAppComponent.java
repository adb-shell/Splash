package com.karthik.splash.DI;

import com.karthik.splash.Modules.FeedsHomeModule;
import com.karthik.splash.Modules.FeedsScreenModule;
import com.karthik.splash.Modules.SplashApiModule;
import com.karthik.splash.Modules.SplashAppModule;
import com.karthik.splash.Modules.SplashScreenModule;
import com.karthik.splash.SplashApp;

import dagger.Component;

/**
 * Created by karthikrk on 12/11/17.
 */
@Component(modules = {SplashAppModule.class, SplashApiModule.class})
public interface SplashAppComponent {
    void inject(SplashApp splashApp);
    SplashScreenComponent plus(SplashScreenModule splashScreenModule);
    FeedsScreenComponent  plus(FeedsScreenModule feedsScreenModule);
    FeedsHomeComponent plus(FeedsHomeModule feedsHomeModule);
}
