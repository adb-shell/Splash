package com.karthik.splash.homescreen.bottomtab.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomtab.IBottomTabRepository
import kotlinx.coroutines.CoroutineScope

class BottomTabDataSourceFactory(
    private val repository: IBottomTabRepository,
    private val intialpaginationdata: BottomTabPaginationData,
    private val coroutineScope: CoroutineScope
) : DataSource.Factory<BottomTabPaginationData, Photos>() {
    val datasource: MutableLiveData<BottomTabDataSource> = MutableLiveData()

    override fun create(): DataSource<BottomTabPaginationData, Photos> {
        val bottomdatasource = BottomTabDataSource(repository, intialpaginationdata, coroutineScope)
        datasource.postValue(bottomdatasource)
        return bottomdatasource
    }
}
