package com.karthik.splash.Contracts;

/**
 * Created by karthikrk on 14/11/17.
 */

public interface FeedsContract {
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
