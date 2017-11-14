package com.karthik.splash.DI;

import com.karthik.splash.Modules.FeedsScreenModule;
import com.karthik.splash.Views.Feeds;

import dagger.Subcomponent;

/**
 * Created by karthikrk on 14/11/17.
 */
@Subcomponent(modules = {FeedsScreenModule.class})
public interface FeedsScreenComponent {
    void inject(Feeds feeds);
}
