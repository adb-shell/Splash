package com.karthik.network.home.models

sealed class HomeScreenLoginState {
    data class LoginSuccess(val userAuth: UserAuth) : HomeScreenLoginState()
    data class LoginFailed(val error: Throwable) : HomeScreenLoginState()
}