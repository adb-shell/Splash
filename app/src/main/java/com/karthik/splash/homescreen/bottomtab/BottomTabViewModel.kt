package com.karthik.splash.homescreen.bottomtab

import com.karthik.splash.homescreen.bottomtab.network.BottomTabRepository
import javax.inject.Inject
import androidx.paging.PagedList
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import com.karthik.splash.homescreen.bottomtab.datasource.BottomTabDataSourceFactory
import com.karthik.splash.homescreen.bottomtab.datasource.BottomTabPaginationData
import com.karthik.splash.homescreen.bottomtab.network.PhotoFeedNetworkState
import com.karthik.splash.models.PhotosLists.Photos



class BottomTabViewModelFactory(private val isCacheAvailable:Boolean,
                                private val bottomTabRepository: BottomTabRepository,
                                private val type:BottomTabTypes):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
            = BottomTabViewModel(isCacheAvailable,bottomTabRepository,type) as T
}


class BottomTabViewModel @Inject constructor(private val isCacheAvailable:Boolean,
                                             private val bottomTabRepository: BottomTabRepository,
                                             type:BottomTabTypes):ViewModel() {
    val feeds: LiveData<PagedList<Photos>>
    val networkState:LiveData<PhotoFeedNetworkState>

    /**
     * Pagination initialisation.
     */
    init {
       val datatsourcefactory = BottomTabDataSourceFactory(bottomTabRepository,
               BottomTabPaginationData(0,BottomTabTypes.convertTabToType(type)))
       val pagedlistconfig = PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(10).build()
       feeds = LivePagedListBuilder(datatsourcefactory,pagedlistconfig).build()
       networkState = Transformations.switchMap(datatsourcefactory.datasource) { datasource->
           datasource.networkState
       }
    }

    override fun onCleared() {
        super.onCleared()
        bottomTabRepository.clearResources()
    }
}