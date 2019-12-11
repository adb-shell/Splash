package com.karthik.splash.root

import android.content.Context
import com.karthik.splash.Storage.Cache
import dagger.Module
import dagger.Provides

@Module
class SplashAppModule(private val context: Context) {

    @Provides
    fun providesContext() = context

    @Provides
    fun providesCache() = Cache(context)
}