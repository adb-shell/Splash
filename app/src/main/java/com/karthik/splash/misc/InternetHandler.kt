package com.karthik.splash.misc

import android.content.Context
import android.net.ConnectivityManager
import com.karthik.network.IInternetHandler

class InternetHandler(private val context: Context) : IInternetHandler {
    override fun isInternetAvailable(): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}