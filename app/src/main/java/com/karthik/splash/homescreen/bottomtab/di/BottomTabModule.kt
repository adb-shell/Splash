package com.karthik.splash.homescreen.bottomtab.di

import android.content.Context
import com.karthik.splash.homescreen.bottomtab.BottomTabTypes
import com.karthik.splash.homescreen.bottomtab.network.BottomTabRepository
import com.karthik.splash.homescreen.bottomtab.BottomTabViewModelFactory
import com.karthik.splash.storage.Cache
import com.karthik.splash.storage.SqlLiteDbHandler
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BottomTabModule{


    private val sqlLiteDbHandler: SqlLiteDbHandler
    private val isCacheAvailable:Boolean
    private val bottomtabtype:BottomTabTypes

    constructor(isCacheAvailable:Boolean?, context: Context?,bottomtabtype: BottomTabTypes?){
        checkNotNull(context) { "context cannot be null" }
        requireNotNull(bottomtabtype) { "type cannot be null" }
        sqlLiteDbHandler = SqlLiteDbHandler(context)
        this.isCacheAvailable = isCacheAvailable != null
        this.bottomtabtype = bottomtabtype
    }

    @Provides
    fun providesDb() = sqlLiteDbHandler

    @Provides
    fun providesNetworkLayer(retrofit: Retrofit,cache: Cache,sqlLiteDbHandler: SqlLiteDbHandler) =
            BottomTabRepository(retrofit, cache, sqlLiteDbHandler)

    @Provides
    fun providesBottomTabVFactory(bottomTabRepository: BottomTabRepository) =
            BottomTabViewModelFactory(isCacheAvailable,bottomTabRepository,bottomtabtype)
}