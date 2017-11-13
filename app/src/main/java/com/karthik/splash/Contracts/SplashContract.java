package com.karthik.splash.Contracts;

import android.content.Context;

/**
 * Created by karthikrk on 12/11/17.
 */

public interface SplashContract {
    interface SplashView{
        Context getSplashContext();
        void showNoInternetScreen();
        void showDashBoardScreen(boolean shouldShowCache);
        void closeScreen();
    }
    interface  SplashPresenter{
        void decideScreens();
        boolean shouldShowCache();
    }
}
