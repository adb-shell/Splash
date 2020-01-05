package com.karthik.splash.homescreen.bottomtab.di

import android.content.Context
import com.karthik.splash.homescreen.bottomtab.network.BottomTabRepository
import com.karthik.splash.homescreen.bottomtab.BottomTabViewModel
import com.karthik.splash.storage.Cache
import com.karthik.splash.storage.SqlLiteDbHandler
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BottomTabModule{


    private val sqlLiteDbHandler: SqlLiteDbHandler
    private val isCacheAvailable:Boolean

    constructor(isCacheAvailable:Boolean?, context: Context?){

        checkNotNull(context) { "context cannot be null" }

        sqlLiteDbHandler = SqlLiteDbHandler(context)
        this.isCacheAvailable = isCacheAvailable != null
    }

    @Provides
    fun providesDb() = sqlLiteDbHandler

    @Provides
    fun providesNetworkLayer(retrofit: Retrofit,cache: Cache,sqlLiteDbHandler: SqlLiteDbHandler) =
            BottomTabRepository(retrofit, cache, sqlLiteDbHandler)

    @Provides
    fun providesBottomTabVFactory(bottomTabRepository: BottomTabRepository) =
            BottomTabViewModel.BottomTabViewModelFactory(isCacheAvailable,bottomTabRepository)
}