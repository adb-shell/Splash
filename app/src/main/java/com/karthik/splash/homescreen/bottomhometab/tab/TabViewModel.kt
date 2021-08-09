package com.karthik.splash.homescreen.bottomhometab.tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomtab.IBottomTabRepository
import com.karthik.splash.homescreen.bottomhometab.BottomHomeTab
import com.karthik.splash.homescreen.bottomhometab.getTheTabType
import com.karthik.splash.homescreen.bottomhometab.tab.datasource.PaginationData
import com.karthik.splash.homescreen.bottomhometab.tab.datasource.SplashPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TabViewModelFactory(
    private val bottomTabRepository: IBottomTabRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            TabViewModel(bottomTabRepository) as T
}


class TabViewModel @Inject constructor(
    private val bottomTabRepository: IBottomTabRepository
) : ViewModel() {

    fun getPhotosStream(type: BottomHomeTab): Flow<PagingData<Photos>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SplashPagingSource(
                    bottomTabRepository = bottomTabRepository,
                    intialpaginationData = PaginationData(
                        pagenumber = 0,
                        mode = type.getTheTabType()
                    )
                )
            }
        ).flow
    }
}
