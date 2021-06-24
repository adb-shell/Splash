package com.karthik.network

import retrofit2.Retrofit
import javax.inject.Inject

class ServiceProvider @Inject constructor(private val retrofit: Retrofit) {
    fun <T> createNetworkService(service: Class<T>): T = retrofit.create(service)
}