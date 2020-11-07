package com.karthik.splash.root

import android.content.Context
import com.karthik.splash.misc.InternetHandler
import com.karthik.splash.storage.MemoryCache
import com.karthik.splash.storage.db.SplashDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SplashAppModule(private val context: Context, private val splashDatabase: SplashDatabase) {

    @Provides
    fun providesContext() =
            context

    @Provides
    fun providesCache() =
            MemoryCache(context)

    @Singleton
    @Provides
    fun providesDatabaseDao() =
            splashDatabase.getDatabaseDao()

    @Singleton
    @Provides
    fun providesInternetHandler() =
            InternetHandler(context)
}