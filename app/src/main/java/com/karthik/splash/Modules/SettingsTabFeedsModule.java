package com.karthik.splash.Modules;

import com.karthik.splash.Contracts.FeedsSettingsContract;
import com.karthik.splash.Presenters.FeedsSettingsPresenter;
import com.karthik.splash.Storage.Cache;

import dagger.Module;
import dagger.Provides;

/**
 * Created by karthikrk on 03/12/17.
 */

@Module
public class SettingsTabFeedsModule {
    private FeedsSettingsContract.FeedsSettingsView view;

    public SettingsTabFeedsModule(FeedsSettingsContract.FeedsSettingsView view){
        this.view = view;
    }

    @Provides
    public FeedsSettingsContract.FeedsSettingsView providesSettingsView(){
        return view;
    }

    @Provides
    public FeedsSettingsContract.FeedsSettingsPresenter providesPresenter(
            FeedsSettingsContract.FeedsSettingsView view,
            Cache cache){
        return new FeedsSettingsPresenter(view,cache);
    }
}
