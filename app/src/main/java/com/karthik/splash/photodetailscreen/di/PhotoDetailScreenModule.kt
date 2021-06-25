package com.karthik.splash.photodetailscreen.di



import com.karthik.network.ServiceProvider
import com.karthik.network.photodetailscreen.IPhotoDetailScreenRepository
import com.karthik.network.photodetailscreen.repository.PhotoDetailScreenRepository
import com.karthik.splash.photodetailscreen.PhotoDetailScreenViewModelFactory
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides

@Module
class PhotoDetailScreenModule {

    @Provides
    fun providesPhotoNetworkLayer(
        serviceProvider: ServiceProvider
    ): IPhotoDetailScreenRepository = PhotoDetailScreenRepository(serviceProvider)

    @Provides
    fun providesPhotoDetailPresenter(
            photoRepository: IPhotoDetailScreenRepository,
            memoryCache: MemoryCache
    ) =
            PhotoDetailScreenViewModelFactory(memoryCache, photoRepository)
}