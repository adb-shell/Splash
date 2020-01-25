package com.karthik.splash.homescreen

import com.karthik.splash.models.oauth.UserAuth

sealed class HomeScreenLoginState {
    data class LoginSuccess(val userAuth: UserAuth):HomeScreenLoginState()
    data class LoginFailed(val error: Throwable):HomeScreenLoginState()
}