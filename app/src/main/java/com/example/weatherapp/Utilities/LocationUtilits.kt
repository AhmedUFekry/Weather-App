package com.example.weatherforecast.utilities

import android.content.Context
import android.location.Geocoder
import java.util.*

object LocationUtils {

    fun getAddress(context: Context, lat: Double, lon: Double): String {
        val address = getFormattedAddress(context, lat, lon)
        return address ?: "Unknown"
    }

    private fun getFormattedAddress(context: Context, lat: Double, lon: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        return addresses?.firstOrNull()?.run {
            "$countryName, $adminArea"
        }
    }
}
