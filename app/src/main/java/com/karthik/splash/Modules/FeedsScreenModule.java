package com.karthik.splash.Modules;

import com.karthik.splash.Contracts.FeedsContract;
import com.karthik.splash.Presenters.FeedsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by karthikrk on 14/11/17.
 */
@Module
public class FeedsScreenModule {
    private FeedsContract.View feedsView;

    public FeedsScreenModule(FeedsContract.View feedsView){
        this.feedsView = feedsView;
    }

    @Provides
    public FeedsContract.View providesFeedsView(){
        return feedsView;
    }

    @Provides
    FeedsContract.Presenter providesPresenter(FeedsContract.View feedsView){
        return new FeedsPresenter(feedsView);
    }
}
