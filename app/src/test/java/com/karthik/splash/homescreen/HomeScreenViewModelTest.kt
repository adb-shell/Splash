package com.karthik.splash.homescreen


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.karthik.splash.homescreen.network.IHomeScreenOAuthRepository
import com.karthik.splash.models.oauth.OAuthBody
import com.karthik.splash.models.oauth.UserAuth
import com.karthik.splash.observeForTesting
import com.karthik.splash.storage.IMemoryCache
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class HomeScreenViewModelTest {

    private lateinit var memoryCache: IMemoryCache
    private lateinit var homeScreenOAuthRepository: IHomeScreenOAuthRepository
    private lateinit var homeViewmodel: HomeScreenViewModel

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        memoryCache = mock()
        homeScreenOAuthRepository = mock()
        homeViewmodel = HomeScreenViewModel(
                memoryCache = memoryCache,
                homeScreenOAuthRepository = homeScreenOAuthRepository
        )
    }


    @Test
    fun `given code is null or empty user login state is login failed`(){
        homeViewmodel.getUserInfo(null)
        homeViewmodel.userloginstate.observeForTesting {  }
        Assert.assertTrue(homeViewmodel.userloginstate.value is HomeScreenLoginState.LoginFailed)
    }

    @Test
    fun `given repository returns success in post of OAuth login state is set as success`() {
        val oAuthBody = OAuthBody("abcdw")
        val userAuth = UserAuth(accessToken = "as",tokenType = "cd",scope = "sas",createdAt = "123456")
        Mockito.`when`(runBlocking { homeScreenOAuthRepository.postOAuth(oAuthBody) })
                .thenReturn(HomeScreenLoginState.LoginSuccess(userAuth))
        homeViewmodel.getUserInfo("abcdw")
        homeViewmodel.userloginstate.observeForTesting {  }
        Assert.assertTrue(homeViewmodel.userloginstate.value is HomeScreenLoginState.LoginSuccess)
    }

    @Test
    fun `given repository returns failure in post of OAuth login state is set as failed`() {
        val oAuthBody = OAuthBody("saasa")
        Mockito.`when`(runBlocking { homeScreenOAuthRepository.postOAuth(oAuthBody) })
                .thenReturn(HomeScreenLoginState.LoginFailed(IllegalArgumentException()))
        homeViewmodel.getUserInfo("saasa")
        homeViewmodel.userloginstate.observeForTesting {  }
        Assert.assertTrue(homeViewmodel.userloginstate.value is HomeScreenLoginState.LoginFailed)
    }
}
