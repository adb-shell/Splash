package com.karthik.network

interface IMemoryCache {
    fun isUserLoggedIn(): Boolean
    fun setUserLoggedIn()
    fun isCacheAvail(): Boolean
    fun setCacheAvail()
    fun setAuthCode(code: String?)
    fun getAuthCode(): String?
    fun setUserName(username: String)
    fun getUserName(): String?
    fun logOutUser()
}