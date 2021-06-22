package com.karthik.splash.photodetailscreen.di



import com.karthik.network.photodetailscreen.IPhotoDetailScreenRepository
import com.karthik.network.photodetailscreen.repository.PhotoDetailScreenRepository
import com.karthik.splash.photodetailscreen.PhotoDetailScreenViewModelFactory
import com.karthik.splash.storage.MemoryCache
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PhotoDetailScreenModule {

    @Provides
    fun providesPhotoNetworkLayer(retrofit: Retrofit): IPhotoDetailScreenRepository {
        return PhotoDetailScreenRepository(retrofit)
    }

    @Provides
    fun providesPhotoDetailPresenter(
            photoRepository: IPhotoDetailScreenRepository,
            memoryCache: MemoryCache
    ) =
            PhotoDetailScreenViewModelFactory(memoryCache, photoRepository)
}