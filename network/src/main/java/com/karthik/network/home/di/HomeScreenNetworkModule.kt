package com.karthik.network.home.di



import com.karthik.network.home.IHomeScreenOAuthRepository
import com.karthik.network.home.repository.HomeScreenOAuthRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class HomeScreenNetworkModule {

    @Provides
    fun providesOAuthNetworkLayer(okHttpClient: OkHttpClient): IHomeScreenOAuthRepository =
            HomeScreenOAuthRepository(okHttpClient)
}
