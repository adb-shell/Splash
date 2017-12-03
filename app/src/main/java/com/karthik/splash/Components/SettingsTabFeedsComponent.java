package com.karthik.splash.Components;

import com.karthik.splash.Modules.SettingsTabFeedsModule;
import com.karthik.splash.Views.FeedsSettings;

import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by karthikrk on 03/12/17.
 */
@Subcomponent(modules = {SettingsTabFeedsModule.class})
public interface SettingsTabFeedsComponent {
    void inject(FeedsSettings feedsSettings);
}
