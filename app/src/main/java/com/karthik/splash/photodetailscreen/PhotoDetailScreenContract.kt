package com.karthik.splash.photodetailscreen

import android.content.Context

import com.karthik.splash.models.photodetail.PhotoDetailInfo

/**
 * Created by karthikrk on 09/12/17.
 */

interface PhotoDetailScreenContract {
    interface view {
        fun isPermissionGranted(): Boolean
        fun showPhotoDetails(photoDetailInfo: PhotoDetailInfo)
        fun showDefaultView()
        fun showLoading()
        fun hideLoading()
        fun showLoginRequired()
        fun errorLikingPhoto()
        fun successLikingPhoto()
        fun showFileDownloadedSuccessMessage(path: String?)
        fun showFileErrorDownloading()
        fun askPermission()
    }

    interface presenter {
        fun getPhotoDetails(id: String)
        fun likeThePhoto(id: String)
        fun downloadPhoto(context: Context, fileName: String, url: String?)
        fun clearResources()
    }
}
