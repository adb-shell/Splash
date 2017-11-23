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
        void showEmptyScreen();
        void showNoInternetScreen();
        boolean isFeedListVisible();
    }
    interface Presenter{
        void getFeeds(int mode,boolean isFromCache,int pageSize);
        void getPaginatedFeeds(int mode,int pageSize);
        void clearResources();
        void manageErrors(Throwable e);
        void managePhotos(List<Photos> photos);
        boolean isPaginatedItems();
        int getPageMaxLimit();
    }
}
