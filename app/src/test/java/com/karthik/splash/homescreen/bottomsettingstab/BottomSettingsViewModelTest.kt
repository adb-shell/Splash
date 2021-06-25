package com.karthik.splash.homescreen.bottomsettingstab

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.karthik.network.IMemoryCache
import com.karthik.splash.models.UserStatus
import com.karthik.splash.observeForTesting
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class BottomSettingsViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private lateinit var memoryCache: IMemoryCache
    private lateinit var bottomSettingsViewModel: BottomSettingsViewModel

    @Before
    fun setup() {
        memoryCache = mock()
    }

    @Test
    fun `given user is not logged in userStatus is UserNotLoggedIn`(){
        Mockito.`when`(memoryCache.isUserLoggedIn()).thenReturn(false)
        bottomSettingsViewModel = BottomSettingsViewModel(memoryCache = memoryCache)
        bottomSettingsViewModel.userStatus.observeForTesting {  }
        Assert.assertTrue(bottomSettingsViewModel.userStatus.value is UserStatus.UserNotLoggedIn)
    }

    @Test
    fun `given user is logged but username is null in userStatus is UserNotLoggedIn`(){
        Mockito.`when`(memoryCache.isUserLoggedIn()).thenReturn(true)
        bottomSettingsViewModel = BottomSettingsViewModel(memoryCache = memoryCache)
        bottomSettingsViewModel.userStatus.observeForTesting {  }
        Assert.assertTrue(bottomSettingsViewModel.userStatus.value is UserStatus.UserNotLoggedIn)
    }

    @Test
    fun `given user is logged in userStatus is UserLoggedIn`(){
        Mockito.`when`(memoryCache.isUserLoggedIn()).thenReturn(true)
        Mockito.`when`(memoryCache.getUserName()).thenReturn("abcd")
        bottomSettingsViewModel = BottomSettingsViewModel(memoryCache = memoryCache)
        bottomSettingsViewModel.userStatus.observeForTesting {  }
        Assert.assertTrue(bottomSettingsViewModel.userStatus.value is UserStatus.UserLoggedIn)
    }
}