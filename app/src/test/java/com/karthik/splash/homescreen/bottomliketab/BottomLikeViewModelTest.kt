package com.karthik.splash.homescreen.bottomliketab

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.karthik.network.IInternetHandler
import com.karthik.network.IMemoryCache
import com.karthik.network.ServiceProvider
import com.karthik.network.home.bottomliketab.repository.BottomLikeTabRepository
import com.karthik.splash.storage.db.SplashDao
import com.karthik.splash.storage.db.entity.PhotosStorage
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class BottomLikeViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private lateinit var memoryCache: IMemoryCache
    private lateinit var internetHandler: IInternetHandler
    private lateinit var bottomlikeViewModel: BottomLikeViewModel
    private lateinit var splashDao: SplashDao
    private lateinit var bottomLikeTabRepository: BottomLikeTabRepository

    @Before
    fun setup() {
        memoryCache = mock()
        internetHandler = mock()
        splashDao = mock()
        val serviceProvider = mock<ServiceProvider>()
        //we will always assume the user is logged in
        Mockito.`when`(memoryCache.isUserLoggedIn()).thenReturn(true)
        Mockito.`when`(memoryCache.getUserName()).thenReturn("abcd")

        bottomLikeTabRepository = BottomLikeTabRepository(
                memoryCache = memoryCache,
                internetHandler = internetHandler,
                serviceProvider = serviceProvider
        )

        bottomlikeViewModel = BottomLikeViewModel(
                memoryCache = memoryCache,
                respository = bottomLikeTabRepository
        )
    }

    @Test
    fun `given bottomlikerepo returns error in-case of no internet connections`() {
        Assert.assertTrue(bottomlikeViewModel.screenStatus.value is LikeScreenNetworkStatus.ScreenLoggedIn)
        Mockito.`when`(internetHandler.isInternetAvailable()).thenReturn(false)
        Mockito.`when`(memoryCache.isCacheAvail()).thenReturn(false)

        bottomlikeViewModel.getLikedPhotos()
        /*bottomlikeViewModel.networkstate.observeForTesting { }

        Assert.assertTrue(bottomlikeViewModel.networkstate.value is LikeFeedNetworkState.FeedNetworkError)*/
    }

    @Test
    fun `given bottomlikerepo returns from nw in case of internet connections`() {
        Assert.assertTrue(bottomlikeViewModel.screenStatus.value is LikeScreenNetworkStatus.ScreenLoggedIn)
        Mockito.`when`(internetHandler.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(memoryCache.isCacheAvail()).thenReturn(false)

        Mockito.`when`(runBlocking { splashDao.getPhotos(1, "LIKE") }).thenReturn(PhotosStorage(
                pagenumber = 1,
                photos = arrayListOf(),
                type = "",
                pgtype = ""))

        bottomlikeViewModel.getLikedPhotos()
        /*bottomlikeViewModel.networkstate.observeForTesting { }

        Assert.assertTrue(bottomlikeViewModel.networkstate.value is LikeFeedNetworkState.FeedNetworkLoading)*/
    }
}
