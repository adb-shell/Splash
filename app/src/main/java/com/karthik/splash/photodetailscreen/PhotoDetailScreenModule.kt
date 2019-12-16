package com.karthik.splash.photodetailscreen

import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PhotoDetailScreenModule(val view: PhotoDetailScreenContract.view) {

    @Provides
    fun providesPhotoDetail() = view

    @Provides
    fun providesPhotoNetworkLayer(retrofit: Retrofit) = PhotoDetailScreenNetworkLayer(retrofit)

    @Provides
    fun providesPhotoDetailPresenter(photoNetworkLayer: PhotoDetailScreenNetworkLayer,
                                     cache: Cache): PhotoDetailScreenContract.presenter =
            PhotoDetailScreenPresenter(view,photoNetworkLayer,cache)
}