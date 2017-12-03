package com.karthik.splash.Components;

import com.karthik.splash.Modules.SplashScreenModule;
import com.karthik.splash.Views.Splash;

import dagger.Subcomponent;

/**
 * Created by karthikrk on 12/11/17.
 */
@Subcomponent(modules = {SplashScreenModule.class})
public interface SplashScreenComponent {
    void inject(Splash splash);
}
