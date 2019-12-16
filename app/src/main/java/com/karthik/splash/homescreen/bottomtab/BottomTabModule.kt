package com.karthik.splash.homescreen.bottomtab

import android.content.Context
import com.karthik.splash.storage.Cache
import com.karthik.splash.storage.SqlLiteDbHandler
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class BottomTabModule{

    private val view: BottomTabContract.View
    private val sqlLiteDbHandler: SqlLiteDbHandler

    constructor(view: BottomTabContract.View,context: Context?){

        checkNotNull(context) { "context cannot be null" }

        this.view = view
        sqlLiteDbHandler = SqlLiteDbHandler(context)
    }

    @Provides
    fun providesBottomTabView() = view

    @Provides
    fun providesDb() = sqlLiteDbHandler

    @Provides
    fun providesNetworkLayer(retrofit: Retrofit,cache: Cache,sqlLiteDbHandler: SqlLiteDbHandler) =
            BottomTabNetworkLayer(retrofit, cache, sqlLiteDbHandler)

    @Provides
    fun providesBottomTabPresenter(bottomTabNetworkLayer: BottomTabNetworkLayer):BottomTabContract.Presenter =
            BottomTabPresenter(view,bottomTabNetworkLayer)
}