package com.karthik.splash.Components;

import com.karthik.splash.Modules.FeedsHomeModule;
import com.karthik.splash.Views.FeedsHome;

import dagger.Subcomponent;

/**
 * Created by karthikrk on 15/11/17.
 */
@Subcomponent(modules = {FeedsHomeModule.class})
public interface FeedsHomeComponent {
    void inject(FeedsHome feedsHome);
}
