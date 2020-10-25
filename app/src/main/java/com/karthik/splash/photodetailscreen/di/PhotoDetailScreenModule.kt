package com.karthik.splash.photodetailscreen.di


import com.karthik.splash.photodetailscreen.IPhotoDetailScreenRepository
import com.karthik.splash.photodetailscreen.network.PhotoDetailScreenRepository
import com.karthik.splash.photodetailscreen.PhotoDetailScreenViewModelFactory
import com.karthik.splash.photodetailscreen.network.PhotoService
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PhotoDetailScreenModule{

    @Provides
    fun providesPhotoNetworkLayer(retrofit: Retrofit): IPhotoDetailScreenRepository{
        val photoService: PhotoService = retrofit.create(PhotoService::class.java)
        return PhotoDetailScreenRepository(photoService = photoService)
    }

    @Provides
    fun providesPhotoDetailPresenter(photoRepository: IPhotoDetailScreenRepository,
                                     memoryCache: MemoryCache)= PhotoDetailScreenViewModelFactory(memoryCache,photoRepository)
}