package com.karthik.splash.photodetailscreen.di


import com.karthik.splash.photodetailscreen.network.PhotoDetailScreenRepository
import com.karthik.splash.photodetailscreen.PhotoDetailScreenViewModelFactory
import com.karthik.splash.storage.Cache
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PhotoDetailScreenModule{

    @Provides
    fun providesPhotoNetworkLayer(retrofit: Retrofit) = PhotoDetailScreenRepository(retrofit)

    @Provides
    fun providesPhotoDetailPresenter(photoRepository: PhotoDetailScreenRepository,
                                     cache: Cache)= PhotoDetailScreenViewModelFactory(cache,photoRepository)
}