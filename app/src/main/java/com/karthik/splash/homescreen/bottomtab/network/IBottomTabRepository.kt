package com.karthik.splash.homescreen.bottomtab.network

interface IBottomTabRepository {
    suspend fun getFeeds(
            pageno: Int = 1,
            type: String
    ): PhotoNetworkResponse
}
