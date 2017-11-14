package com.karthik.splash.Contracts;

/**
 * Created by karthikrk on 14/11/17.
 */

public interface FeedsContract {
    interface View{
        void inflateHome();
        void inflateLikes();
        void inflateSettings();
    }
    interface Presenter{
        void onNavigationItemSelected(int id);
    }
}
