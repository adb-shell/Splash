package com.karthik.splash.homescreen;

/**
 * Created by karthikrk on 14/11/17.
 */

public interface HomeScreenContract {
    interface View{
        void inflateHome();
        void inflateLikes();
        void inflateSettings();
        int getSelectedItem();
        void displayUnableToLogin();
    }
    interface Presenter{
        void onNavigationItemSelected(int id);
        void getUserDetail(String code);
        void clearResource();
    }
}
