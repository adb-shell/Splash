package com.karthik.splash.homescreen.bottomhometab.tab.datasource


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomtab.IBottomTabRepository
import com.karthik.network.home.bottomtab.models.PhotoNetworkResponse

class SplashPagingSource(
    private val bottomTabRepository: IBottomTabRepository,
    private val intialpaginationData: PaginationData
) : PagingSource<PaginationData, Photos>() {

    override fun getRefreshKey(state: PagingState<PaginationData, Photos>): PaginationData? =
        null

    override suspend fun load(
        params: LoadParams<PaginationData>
    ): LoadResult<PaginationData, Photos> {
        return when (params) {
            is LoadParams.Prepend -> {
                fetchData(PaginationData(1, intialpaginationData.mode))
            }
            is LoadParams.Append -> {
                fetchData(PaginationData(params.key.pagenumber, params.key.mode))
            }
            is LoadParams.Refresh -> {
                fetchData(PaginationData(1, intialpaginationData.mode))
            }
        }
    }

    private suspend fun fetchData(
        pageData: PaginationData
    ): LoadResult<PaginationData, Photos> {
        return when (val response = bottomTabRepository.getFeeds(
            pageno = pageData.pagenumber,
            type = pageData.mode
        )) {
            is PhotoNetworkResponse.FeedSuccessResponse -> {
                LoadResult.Page(
                    data = response.photos,
                    prevKey = null,
                    nextKey = PaginationData(
                        pageData.pagenumber + 1,
                        pageData.mode
                    )
                )
            }
            is PhotoNetworkResponse.FeedFailureResponse -> {
                LoadResult.Error(response.error)
            }
        }
    }
}
