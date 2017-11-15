package com.karthik.splash.Modules;

import com.karthik.splash.Contracts.FeedsHomeContract;
import com.karthik.splash.Presenters.FeedsHomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by karthikrk on 15/11/17.
 */
@Module
public class FeedsHomeModule {

    private FeedsHomeContract.view view;

    public FeedsHomeModule(FeedsHomeContract.view view){
        this.view = view;
    }

    @Provides
    public FeedsHomeContract.view providesView(){
        return view;
    }

    @Provides
    public FeedsHomeContract.presenter providesPresenter(FeedsHomeContract.view view){
        return new FeedsHomePresenter(view);
    }
}
