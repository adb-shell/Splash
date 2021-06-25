package com.karthik.network.home.bottomtab.repository

import com.karthik.network.IInternetHandler
import com.karthik.network.ServiceProvider
import com.karthik.network.UserOfflineException
import com.karthik.network.home.bottomtab.IBottomTabRepository
import com.karthik.network.home.bottomtab.models.PhotoNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//TODO: cache response
class BottomTabRepository(
    private val internetHandler: IInternetHandler,
    serviceProvider: ServiceProvider
) : IBottomTabRepository {

    private val bottomTabNetworkService =
        serviceProvider.createNetworkService(BottomTabNetworkService::class.java)

    companion object {
        const val SORT_BY_LATEST = "latest"
        const val SORT_BY_POPULAR = "popular"
        const val SORT_BY_OLDEST = "oldest"
    }

    override suspend fun getFeeds(pageno: Int, type: String): PhotoNetworkResponse {
        return withContext(Dispatchers.IO) {
            if (!internetHandler.isInternetAvailable()) {
                //return cached response
                PhotoNetworkResponse.FeedFailureResponse(UserOfflineException())
            }
            val photosResponse = bottomTabNetworkService.getPhotos(type, pageno)
            if (photosResponse.isSuccessful) {
                photosResponse.body()?.let { photos ->
                    PhotoNetworkResponse.FeedSuccessResponse(photos = photos)
                } ?: PhotoNetworkResponse.FeedFailureResponse(IllegalStateException())
            } else {
                PhotoNetworkResponse.FeedFailureResponse(IllegalStateException())
            }
        }
    }
}
