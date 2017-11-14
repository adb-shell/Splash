package com.karthik.splash.Presenters;

import com.karthik.splash.Contracts.FeedsContract;
import com.karthik.splash.R;

import javax.inject.Inject;

/**
 * Created by karthikrk on 14/11/17.
 */

public class FeedsPresenter implements FeedsContract.Presenter{
    FeedsContract.View feedsView;

    @Inject
    public  FeedsPresenter(FeedsContract.View feedsView){
        this.feedsView = feedsView;
    }

    @Override
    public void onNavigationItemSelected(int id) {
        switch (id){
            case R.id.navigation_home:
                feedsView.inflateHome();
                break;
            case R.id.navigation_likes:
                feedsView.inflateLikes();
                break;
            case R.id.navigation_settings:
                feedsView.inflateSettings();
                break;
        }
    }
}
