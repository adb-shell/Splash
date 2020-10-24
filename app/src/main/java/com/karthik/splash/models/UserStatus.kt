package com.karthik.splash.models

sealed class UserStatus {
    data class UserLoggedIn(val username: String) : UserStatus()
    object UserNotLoggedIn : UserStatus()
}