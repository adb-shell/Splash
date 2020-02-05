package com.karthik.splash.photodetailscreen.di


import com.karthik.splash.photodetailscreen.network.PhotoDetailScreenRepository
import com.karthik.splash.photodetailscreen.PhotoDetailScreenViewModelFactory
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PhotoDetailScreenModule{

    @Provides
    fun providesPhotoNetworkLayer(retrofit: Retrofit) = PhotoDetailScreenRepository(retrofit)

    @Provides
    fun providesPhotoDetailPresenter(photoRepository: PhotoDetailScreenRepository,
                                     memoryCache: MemoryCache)= PhotoDetailScreenViewModelFactory(memoryCache,photoRepository)
}