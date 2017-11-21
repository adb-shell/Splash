package com.karthik.splash.DI;

import com.karthik.splash.Modules.HomeTabFeedsModule;
import com.karthik.splash.Views.HomeTabFeeds;

import dagger.Subcomponent;

/**
 * Created by karthikrk on 16/11/17.
 */
@Subcomponent(modules = {HomeTabFeedsModule.class})
public interface HomeTabFeedsComponent {
    void inject(HomeTabFeeds homeTabFeeds);
}
