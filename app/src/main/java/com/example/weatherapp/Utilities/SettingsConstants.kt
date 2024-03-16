package com.example.weatherapp.Utilities


import com.example.weatherapp.R

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
    fun getLocation(): Int {
        return when (location) {
            Location.GPS -> {
                R.id.homeFragment
            }
            Location.MAP -> {
                R.id.mapFragment
            }
        }
    }

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
