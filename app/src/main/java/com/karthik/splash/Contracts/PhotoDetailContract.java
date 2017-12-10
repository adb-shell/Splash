package com.karthik.splash.Contracts;

import android.content.Context;

import com.karthik.splash.Models.PhotoDetail.PhotoDetailInfo;

/**
 * Created by karthikrk on 09/12/17.
 */

public interface PhotoDetailContract {
    interface PhotoDetailView{
        void showPhotoDetails(PhotoDetailInfo photoDetailInfo);
        void showDefaultView();
        void showLoading();
        void hideLoading();
        void showLoginRequired();
        void errorLikingPhoto();
        void successLikingPhoto();
        void showFileDownloadedSuccessMessage(String path);
        void showFileErrorDownloading();
        boolean isPermissionGranted();
        void askPermission();
    }

    interface PhotoDetailPresenter{
        void getPhotoDetails(String id);
        void likeThePhoto(String id);
        void downloadPhoto(Context context, String fileName, String url);
    }
}
