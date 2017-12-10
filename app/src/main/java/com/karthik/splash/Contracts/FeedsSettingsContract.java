package com.karthik.splash.Contracts;

/**
 * Created by karthikrk on 03/12/17.
 */

public interface FeedsSettingsContract {
    interface FeedsSettingsView{
        void showLoggedInView(String username);
        void showNonLoggedInView();
        void openFolder();
        boolean isReadPermissionGranted();
        void askReadPermission();
    }
    interface FeedsSettingsPresenter{
        void decideScreen();
        void logOutUser();
        void showDownloadedImages();
    }
}
