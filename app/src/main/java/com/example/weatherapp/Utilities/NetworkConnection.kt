package com.example.weatherforecast.utilities

import android.content.Context
import android.net.ConnectivityManager

object NetworkConnection {

    fun checkConnectionState(activity: Context): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.activeNetworkInfo?.run {
            isAvailable && isConnected
        } ?: false
    }
}