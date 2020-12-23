package com.karthik.splash.homescreen.bottomtab.network

import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.models.photoslists.Photos
import com.karthik.splash.restserviceutility.UserOfflineException
import com.karthik.splash.storage.MemoryCache
import com.karthik.splash.storage.db.SplashDao
import com.karthik.splash.storage.db.entity.PhotosStorage
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.lang.IllegalStateException

class BottomTabRepository(
        private val bottomTabNetworkService: BottomTabNetworkService,
        private val localdb: SplashDao,
        private val cache: MemoryCache,
        private val internetHandler: InternetHandler
) : IBottomTabRepository {

    companion object {
        const val SORT_BY_LATEST = "latest"
        const val SORT_BY_POPULAR = "popular"
        const val SORT_BY_OLDEST = "oldest"
    }

    override suspend fun getFeeds(pageno: Int, type: String): PhotoNetworkResponse {
        return withContext(Dispatchers.IO) {
            if (!internetHandler.isInternetAvailable()) {
                //return cached response
                if (cache.isCacheAvail()) {
                    val photosStorage = localdb.getPhotos(page = pageno, type = type)
                    PhotoNetworkResponse.FeedSuccessResponse(photos = photosStorage.photos)
                } else {
                    PhotoNetworkResponse.FeedFailureResponse(UserOfflineException())
                }
            }
            val photosResponse = bottomTabNetworkService.getPhotos(type, pageno)
            if (photosResponse.isSuccessful) {
                photosResponse.body()?.let { photos ->
                    localdb.setPhotos(PhotosStorage(pagenumber = pageno, type = type,
                            photos = photos, pgtype = "$pageno:$type"))
                    PhotoNetworkResponse.FeedSuccessResponse(photos = photos)
                } ?: PhotoNetworkResponse.FeedFailureResponse(IllegalStateException())
            } else {
                PhotoNetworkResponse.FeedFailureResponse(IllegalStateException())
            }
        }
    }
}
