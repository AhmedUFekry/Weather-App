package com.example.weatherapp.Utilities

import android.content.Context
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.weatherapp.R
import com.example.weatherforecast.sharedprefernces.SharedPreferencesHelper

object SettingsConstants {
    var location: Location = Location.GPS
    var temperature: Temperature = Temperature.C
    var lang: Lang = Lang.EN
    var windSpeed: WindSpeed = WindSpeed.M_S
    var notificationState: NotificationState? = null

    enum class Location { MAP, GPS }
    enum class Temperature { C, K, F }
    enum class Lang { AR, EN }
    enum class WindSpeed { M_S, MILE_HOUR }
    enum class NotificationState { ON, OFF }

    fun getLangCode(): String = lang.name.toLowerCase()

    fun getTemperatureSymbol(): Char = when (temperature) {
        Temperature.F -> 'F'
        Temperature.K -> 'K'
        else -> 'C'
    }

    fun getWindSpeedUnit(): String {
        return when (windSpeed) {
            WindSpeed.MILE_HOUR -> if (lang == Lang.EN) " Mile/H" else " م/س"
            else -> if (lang == Lang.EN) " M/S" else " م/ث"
        }
    }
}
