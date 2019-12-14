package com.karthik.splash.photodetailscreen

import com.karthik.splash.RestServices.NetworkLayer.PhotoNetworkLayer
import com.karthik.splash.Storage.Cache
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PhotoDetailScreenModule(val view: PhotoDetailScreenContract.view) {

    @Provides
    fun providesPhotoDetail() = view

    @Provides
    fun providesPhotoNetworkLayer(retrofit: Retrofit) = PhotoNetworkLayer(retrofit)

    @Provides
    fun providesPhotoDetailPresenter(photoNetworkLayer: PhotoNetworkLayer,
                                     cache: Cache): PhotoDetailScreenContract.presenter =
            PhotoDetailScreenPresenter(view,photoNetworkLayer,cache)
}