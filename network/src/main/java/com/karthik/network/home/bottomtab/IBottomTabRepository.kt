package com.karthik.network.home.bottomtab

import com.karthik.network.home.bottomtab.models.PhotoNetworkResponse

interface IBottomTabRepository {
    suspend fun getFeeds(
            pageno: Int = 1,
            type: String
    ): PhotoNetworkResponse
}
