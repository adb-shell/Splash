package com.karthik.splash.homescreen.bottomtab.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.karthik.splash.homescreen.bottomtab.network.BottomTabRepository
import com.karthik.splash.models.photoslists.Photos

class BottomTabDataSourceFactory(
        private val repository: BottomTabRepository,
        private val intialpaginationdata: BottomTabPaginationData
) : DataSource.Factory<BottomTabPaginationData, Photos>() {
    val datasource: MutableLiveData<BottomTabDataSource> = MutableLiveData()

    override fun create(): DataSource<BottomTabPaginationData, Photos> {
        val bottomdatasource = BottomTabDataSource(repository, intialpaginationdata)
        datasource.postValue(bottomdatasource)
        return bottomdatasource
    }
}