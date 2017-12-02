package com.karthik.splash.Contracts;

import com.karthik.splash.Models.PhotosLists.Photos;

import java.util.List;

/**
 * Created by karthikrk on 25/11/17.
 */

public interface FeedsLikesContract {
    interface view{
        void showLoginScreen();
        void showLikesList(List<Photos> likedPhotos);
        void showProgress();
        void hideProgress();
        void showEmptyLikedScreen();
        void openLoginOauthUrl(String oauthurl);
    }
    interface presenter{
        void decideScreenType();
        void login();
        void clearResources();
    }
}
