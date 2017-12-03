package com.karthik.splash.Components;

import com.karthik.splash.Modules.LikeTabFeedsModule;
import com.karthik.splash.Views.FeedsLike;

import dagger.Subcomponent;

/**
 * Created by karthikrk on 25/11/17.
 */
@Subcomponent(modules = {LikeTabFeedsModule.class})
public interface LikeTabFeedsComponent {
    void inject(FeedsLike feedsLike);
}
