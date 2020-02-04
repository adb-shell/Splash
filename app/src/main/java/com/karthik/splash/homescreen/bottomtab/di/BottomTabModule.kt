package com.karthik.splash.homescreen.bottomtab.di

import android.content.Context
import com.karthik.splash.homescreen.bottomtab.BottomTabTypes
import com.karthik.splash.homescreen.bottomtab.network.BottomTabRepository
import com.karthik.splash.homescreen.bottomtab.BottomTabViewModelFactory
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
import com.karthik.splash.storage.SqlLiteDbHandler
import com.karthik.splash.storage.db.SplashDao
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

    @Provides @Deprecated("Will be removed soon")
    fun providesDb() = sqlLiteDbHandler

    @Provides
    fun providesNetworkLayer(retrofit: Retrofit,splashDao: SplashDao,cache: MemoryCache,internetHandler: InternetHandler) =
            BottomTabRepository(retrofit,splashDao,cache,internetHandler)

    @Provides
    fun providesBottomTabVFactory(bottomTabRepository: BottomTabRepository) =
            BottomTabViewModelFactory(isCacheAvailable,bottomTabRepository,bottomtabtype)
}