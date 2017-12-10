package com.karthik.splash.Contracts;

/**
 * Created by karthikrk on 03/12/17.
 */

public interface FeedsSettingsContract {
    interface FeedsSettingsView{
        void showLoggedInView(String username);
        void showNonLoggedInView();
    }
    interface FeedsSettingsPresenter{
        void decideScreen();
        void logOutUser();
    }
}
