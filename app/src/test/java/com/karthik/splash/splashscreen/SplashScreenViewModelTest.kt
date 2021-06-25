package com.karthik.splash.splashscreen


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.karthik.network.IInternetHandler
import com.karthik.network.IMemoryCache
import com.karthik.splash.observeForTesting
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class SplashScreenViewModelTest {

    private lateinit var internetHandler: IInternetHandler
    private lateinit var memoryCache: IMemoryCache

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        internetHandler = mock()
        memoryCache = mock()
    }

    @Test
    fun `given no internet connection getViewState() returns NoInternetScreen`() {
        Mockito.`when`(internetHandler.isInternetAvailable()).thenReturn(false)
        val splashScreenViewModel = SplashScreenViewModel(internetHandler = internetHandler, memoryCache = memoryCache)
        splashScreenViewModel.splashscreenstate.observeForTesting { }
        Assert.assertEquals(splashScreenViewModel.splashscreenstate.value == SplashScreenState.NoInternetScreen, true)
    }

    @Test
    fun `given memory cache is available and no internet getViewState() returns CachedDashBoardScreen`() {
        Mockito.`when`(internetHandler.isInternetAvailable()).thenReturn(false)
        Mockito.`when`(memoryCache.isCacheAvail()).thenReturn(true)
        val splashScreenViewModel = SplashScreenViewModel(internetHandler = internetHandler, memoryCache = memoryCache)
        splashScreenViewModel.splashscreenstate.observeForTesting { }
        Assert.assertEquals(splashScreenViewModel.splashscreenstate.value == SplashScreenState.CachedDashBoardScreen, true)
    }

    @Test
    fun `given internet is available getViewState() returns FreshDashBoardScreen`() {
        Mockito.`when`(internetHandler.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(memoryCache.isCacheAvail()).thenReturn(false)
        val splashScreenViewModel = SplashScreenViewModel(internetHandler = internetHandler, memoryCache = memoryCache)
        splashScreenViewModel.splashscreenstate.observeForTesting { }
        Assert.assertEquals(splashScreenViewModel.splashscreenstate.value == SplashScreenState.FreshDashBoardScreen, true)
    }
}