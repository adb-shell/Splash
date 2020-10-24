package com.karthik.splash.homescreen.bottomliketab

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.karthik.splash.homescreen.bottomliketab.network.BottomLikeTabNetworkService
import com.karthik.splash.homescreen.bottomliketab.network.BottomLikeTabRepository
import com.karthik.splash.homescreen.bottomliketab.network.LikeFeedNetworkState
import com.karthik.splash.misc.IInternetHandler
import com.karthik.splash.models.UserStatus
import com.karthik.splash.observeForTesting
import com.karthik.splash.storage.IMemoryCache
import com.karthik.splash.storage.db.SplashDao
import com.karthik.splash.storage.db.entity.PhotosStorage
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class BottomLikeViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private lateinit var memoryCache: IMemoryCache
    private lateinit var internetHandler: IInternetHandler
    private lateinit var bottomlikeViewModel: BottomLikeViewModel
    private lateinit var bottomTabLikeTabNetworkService: BottomLikeTabNetworkService
    private lateinit var splashDao: SplashDao
    private lateinit var bottomLikeTabRepository: BottomLikeTabRepository

    @Before
    fun setup() {
        memoryCache = mock()
        internetHandler = mock()
        splashDao = mock()
        bottomTabLikeTabNetworkService = mock()
        //we will always assume the user is logged in
        Mockito.`when`(memoryCache.isUserLoggedIn()).thenReturn(true)
        Mockito.`when`(memoryCache.getUserName()).thenReturn("abcd")

        bottomLikeTabRepository = BottomLikeTabRepository(
                bottomLikeTabNetworkService = bottomTabLikeTabNetworkService,
                memoryCache = memoryCache,
                localdb = splashDao,
                internetHandler = internetHandler
        )

        bottomlikeViewModel = BottomLikeViewModel(
                memoryCache = memoryCache,
                respository = bottomLikeTabRepository
        )
    }

    @Test
    fun `given bottomlikerepo returns error in-case of no internet connections`() {
        Assert.assertTrue(bottomlikeViewModel.isuserloggedin.value is UserStatus.UserLoggedIn)
        Mockito.`when`(internetHandler.isInternetAvailable()).thenReturn(false)
        Mockito.`when`(memoryCache.isCacheAvail()).thenReturn(false)

        bottomlikeViewModel.getLikedPhotos()
        bottomlikeViewModel.networkstate.observeForTesting { }

        Assert.assertTrue(bottomlikeViewModel.networkstate.value is LikeFeedNetworkState.FeedNetworkError)
    }

    @Test
    fun `given bottomlikerepo returns from nw in case of internet connections`() {
        Assert.assertTrue(bottomlikeViewModel.isuserloggedin.value is UserStatus.UserLoggedIn)
        Mockito.`when`(internetHandler.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(memoryCache.isCacheAvail()).thenReturn(false)
        Mockito.`when`(bottomTabLikeTabNetworkService.getUserLikePhotos("abcd")).thenReturn(Single.fromCallable { arrayListOf() })
        Mockito.`when`(splashDao.getPhotos(1, "LIKE")).thenReturn(Maybe.fromCallable {
            PhotosStorage(
                    pagenumber = 1,
                    photos = arrayListOf(),
                    type = "",
                    pgtype = "")
        })

        bottomlikeViewModel.getLikedPhotos()
        bottomlikeViewModel.networkstate.observeForTesting { }

        Assert.assertTrue(bottomlikeViewModel.networkstate.value is LikeFeedNetworkState.FeedNetworkLoading)

        bottomLikeTabRepository.getUserLikedPhotos({
            Assert.assertTrue(bottomlikeViewModel.networkstate.value is LikeFeedNetworkState.FeedNetworkLoadSuccess)
        },{})
    }
}