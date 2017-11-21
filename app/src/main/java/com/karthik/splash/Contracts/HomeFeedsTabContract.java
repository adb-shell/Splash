package com.karthik.splash.Contracts;

import com.karthik.splash.Models.Photos;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by karthikrk on 16/11/17.
 */

public interface HomeFeedsTabContract {
    interface View{
        void hideProgressBar();
        void showPhotosList(List<Photos> photos);
        void showProgressBar();
        void showEmptyScreen();
        void showNoInternetScreen();
    }
    interface Presenter{
        void getFeeds(int mode,boolean isFromCache);
        void clearResources();
        void manageErrors(Throwable e);
        void managePhotos(List<Photos> photos);
        Disposable getNewFeedsFromCacheFirst();
        Disposable getNewFeedsFromNetworkFirst();
        Disposable getTrendingFeedsFromCacheFirst();
        Disposable getTrendingFeedsFromNetworkFirst();
        Disposable getFeaturedFeedsFromCacheFirst();
        Disposable getFeaturedFeedsFromNetworkFirst();
    }
}
