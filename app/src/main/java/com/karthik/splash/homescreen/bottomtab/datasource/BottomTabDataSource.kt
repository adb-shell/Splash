package com.karthik.splash.homescreen.bottomtab.datasource


import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.karthik.splash.homescreen.bottomtab.network.PhotoFeedNetworkState
import com.karthik.splash.homescreen.bottomtab.network.BottomTabRepository
import com.karthik.splash.models.photoslists.Photos

class BottomTabDataSource(
        private val bottomTabRepository: BottomTabRepository,
        private val intialpaginationData: BottomTabPaginationData
) : PageKeyedDataSource<BottomTabPaginationData, Photos>() {

    val networkState: MutableLiveData<PhotoFeedNetworkState> = MutableLiveData()

    override fun loadBefore(
            params: LoadParams<BottomTabPaginationData>,
            callback: LoadCallback<BottomTabPaginationData, Photos>
    ) {
        //TODO:log to crashlytics
    }

    override fun loadInitial(
            params: LoadInitialParams<BottomTabPaginationData>,
            callback: LoadInitialCallback<BottomTabPaginationData, Photos>
    ) {
        bottomTabRepository.getFeeds(pageno = 1,
                type = intialpaginationData.mode,
                successhander = { photos ->
                    callback.onResult(photos,
                            null,
                            BottomTabPaginationData(2, intialpaginationData.mode))
                    networkState.postValue(PhotoFeedNetworkState.FeedNetworkLoadSuccess)
                },
                errorhandler = { error ->
                    networkState.postValue(PhotoFeedNetworkState.FeedNetworkError(error))
                })
    }

    override fun loadAfter(
            params: LoadParams<BottomTabPaginationData>,
            callback: LoadCallback<BottomTabPaginationData, Photos>
    ) {
        networkState.postValue(PhotoFeedNetworkState.FeedNetworkPaginationLoading)
        bottomTabRepository.getFeeds(pageno = params.key.pagenumber,
                type = params.key.mode,
                successhander = { photos ->
                    callback.onResult(photos,
                            BottomTabPaginationData(params.key.pagenumber + 1, params.key.mode))
                    networkState.postValue(PhotoFeedNetworkState.FeedNetworkPaginationLoadSuccess)
                },
                errorhandler = { error ->
                    networkState.postValue(PhotoFeedNetworkState.FeedNetworkPaginationLoadError(
                            error))
                })
    }
}