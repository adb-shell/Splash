package com.karthik.splash.homescreen.bottomtab

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.karthik.splash.homescreen.bottomtab.datasource.BottomTabDataSourceFactory
import com.karthik.splash.homescreen.bottomtab.datasource.BottomTabPaginationData
import com.karthik.splash.homescreen.bottomtab.network.IBottomTabRepository
import com.karthik.splash.homescreen.bottomtab.network.PhotoFeedNetworkState
import com.karthik.splash.misc.Utils
import com.karthik.splash.models.photoslists.Photos
import javax.inject.Inject


class BottomTabViewModelFactory(
        private val bottomTabRepository: IBottomTabRepository,
        private val type: BottomTabTypes
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            BottomTabViewModel(bottomTabRepository, type) as T
}


class BottomTabViewModel @Inject constructor(
        bottomTabRepository: IBottomTabRepository,
        type: BottomTabTypes
) : ViewModel() {
    val feeds: LiveData<PagedList<Photos>>
    val networkState: LiveData<PhotoFeedNetworkState>

    /**
     * Pagination initialisation.
     */
    init {
        val datatsourcefactory = BottomTabDataSourceFactory(
                repository = bottomTabRepository,
                intialpaginationdata = BottomTabPaginationData(
                        pagenumber = 0,
                        mode = BottomTabTypes.convertTabToType(type)
                ),
                coroutineScope = viewModelScope
        )
        val pagedlistconfig = PagedList.Config.Builder()
                .setInitialLoadSizeHint(Utils.intialPageSize)
                .setPageSize(Utils.pageSize).build()
        feeds = LivePagedListBuilder(datatsourcefactory, pagedlistconfig).build()
        networkState = Transformations.switchMap(datatsourcefactory.datasource) { datasource ->
            datasource.networkState
        }
    }
}
