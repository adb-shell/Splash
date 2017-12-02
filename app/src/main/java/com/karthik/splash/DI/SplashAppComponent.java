package com.karthik.splash.DI;

import com.karthik.splash.Modules.FeedsHomeModule;
import com.karthik.splash.Modules.FeedsScreenModule;
import com.karthik.splash.Modules.HomeTabFeedsModule;
import com.karthik.splash.Modules.LikeTabFeedsModule;
import com.karthik.splash.Modules.SplashApiModule;
import com.karthik.splash.Modules.SplashAppModule;
import com.karthik.splash.Modules.SplashScreenModule;
import com.karthik.splash.SplashApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by karthikrk on 12/11/17.
 */
@Singleton
@Component(modules = {SplashAppModule.class, SplashApiModule.class})
public interface SplashAppComponent {
    void inject(SplashApp splashApp);
    SplashScreenComponent plus(SplashScreenModule splashScreenModule);
    FeedsScreenComponent  plus(FeedsScreenModule feedsScreenModule);
    FeedsHomeComponent plus(FeedsHomeModule feedsHomeModule);
    HomeTabFeedsComponent plus(HomeTabFeedsModule homeTabFeedsModule);
    LikeTabFeedsComponent plus(LikeTabFeedsModule likeTabFeedsModule);
}
