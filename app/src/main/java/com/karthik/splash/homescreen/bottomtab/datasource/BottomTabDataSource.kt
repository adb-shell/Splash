package com.karthik.splash.homescreen.bottomtab.datasource


import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.splash.homescreen.bottomtab.network.IBottomTabRepository
import com.karthik.splash.homescreen.bottomtab.network.PhotoFeedNetworkState
import com.karthik.splash.homescreen.bottomtab.network.PhotoNetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BottomTabDataSource(
        private val bottomTabRepository: IBottomTabRepository,
        private val intialpaginationData: BottomTabPaginationData,
        private val coroutineScope: CoroutineScope
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
        coroutineScope.launch {
            val photoFeedsResponse = bottomTabRepository.getFeeds(
                    pageno = 1,
                    type = intialpaginationData.mode)
            when (photoFeedsResponse) {
                is PhotoNetworkResponse.FeedSuccessResponse -> {
                    callback.onResult(
                            photoFeedsResponse.photos,
                            null,
                            BottomTabPaginationData(2, intialpaginationData.mode))
                    networkState.postValue(PhotoFeedNetworkState.FeedNetworkLoadSuccess)
                }
                is PhotoNetworkResponse.FeedFailureResponse -> {
                    networkState.postValue(PhotoFeedNetworkState.FeedNetworkError(
                            error = photoFeedsResponse.error
                    ))
                }
            }
        }
    }

    override fun loadAfter(
            params: LoadParams<BottomTabPaginationData>,
            callback: LoadCallback<BottomTabPaginationData, Photos>
    ) {
        networkState.postValue(PhotoFeedNetworkState.FeedNetworkPaginationLoading)

        coroutineScope.launch {
            val photoFeedsResponse = bottomTabRepository.getFeeds(
                    pageno = params.key.pagenumber,
                    type = params.key.mode)

            when (photoFeedsResponse) {
                is PhotoNetworkResponse.FeedSuccessResponse -> {
                    callback.onResult(photoFeedsResponse.photos,
                            BottomTabPaginationData(
                                    pagenumber = params.key.pagenumber + 1,
                                    mode = params.key.mode))
                    networkState.postValue(PhotoFeedNetworkState.FeedNetworkPaginationLoadSuccess)
                }
                is PhotoNetworkResponse.FeedFailureResponse -> {
                    networkState.postValue(PhotoFeedNetworkState.FeedNetworkPaginationLoadError(
                            error = photoFeedsResponse.error
                    ))
                }
            }
        }
    }
}
