package com.karthik.splash.photodetailscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import com.karthik.splash.observeForTesting
import com.karthik.splash.photodetailscreen.network.PhotoDetailScreenRepository
import com.karthik.splash.photodetailscreen.network.PhotoDetailsNetworkState
import com.karthik.splash.photodetailscreen.network.PhotoLikeResponse
import com.karthik.splash.photodetailscreen.network.PhotoService
import com.karthik.splash.storage.IMemoryCache
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class PhotoDetailScreenViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private lateinit var memoryCache: IMemoryCache
    private lateinit var photoService:PhotoService
    private lateinit var photodetailRepository: IPhotoDetailScreenRepository
    private lateinit var photoDetailScreenViewModel: PhotoDetailScreenViewModel

    @Before
    fun setup() {
        memoryCache = mock()
        photoService = mock()
        photodetailRepository = PhotoDetailScreenRepository(photoService = photoService)
        photoDetailScreenViewModel = PhotoDetailScreenViewModel(
                memoryCache = memoryCache,
                photoRepository = photodetailRepository
        )
    }

    @Test
    fun `given an success in getting getPhotoInfo() state is set has PhotoDetailsNetworkLoadSuccess`(){
        Mockito.`when`(runBlocking { photoService.getPhotoInfo("123") })
                .thenReturn(Response.success(200, PhotoDetailInfo()))
        photoDetailScreenViewModel.getnetworkState().observeForTesting {  }
        runBlocking {
            photodetailRepository.getPhotoInfo("123")
            Assert.assertTrue(photoDetailScreenViewModel.getnetworkState().value is PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
        }
    }

    @Test
    fun `given an success in getting likeThePhoto() state is set has PhotoDetailsNetworkLoadSuccess`(){
        Mockito.`when`(runBlocking { photoService.likePhoto("123") })
                .thenReturn(Response.success(200, LikeResponse()))
        photoDetailScreenViewModel.getnetworkState().observeForTesting {  }
        runBlocking {
            photodetailRepository.likePhoto("123")
            Assert.assertTrue(photoDetailScreenViewModel.getnetworkState().value is PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
        }
    }

    @Test
    fun `given an error in getting likeThePhoto() state is set has PhotoDetailsNetworkLoadError`(){
        Mockito.`when`(runBlocking { photoService.likePhoto("123") })
                .thenReturn(Response.error(400,ResponseBody.create(MediaType.parse("application/json"),
                        "{}")))
        photoDetailScreenViewModel.getnetworkState().observeForTesting {  }
        runBlocking {
            photodetailRepository.likePhoto("123")
            Assert.assertTrue(photoDetailScreenViewModel.getnetworkState().value is PhotoDetailsNetworkState.PhotoLikeNetworkLoadError)
        }
    }

    @Test
    fun `given an error in getting getPhotoInfo() state is set has PhotoDetailsNetworkLoadError`(){
        Mockito.`when`(runBlocking { photoService.getPhotoInfo("123") })
                .thenReturn(Response.error(400, ResponseBody.create(MediaType.parse("application/json"),
                        "{}")))
        photoDetailScreenViewModel.getnetworkState().observeForTesting {  }
        runBlocking {
            photodetailRepository.getPhotoInfo("123")
            Assert.assertTrue(photoDetailScreenViewModel.getnetworkState().value is PhotoDetailsNetworkState.PhotoDetailsNetworkLoadError)
        }
    }
}
