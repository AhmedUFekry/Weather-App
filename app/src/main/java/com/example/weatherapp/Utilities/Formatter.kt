package com.example.weatherapp.Utilities


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Formatter {


    fun convertUnixToTime(unixSeconds: Long): String {
        val milliseconds = unixSeconds * 1000L
        val date = Date(milliseconds)
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return timeFormat.format(date)
    }

    fun getSunriseAndSunset(sunrise: Long, sunset: Long): Pair<String, String> {
        val sunriseTime = convertUnixToTime(sunrise)
        val sunsetTime = convertUnixToTime(sunset)
        return Pair(sunriseTime, sunsetTime)
    }


    fun getFormattedHour(hour: Long?): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        val date = Date(hour?.times(1000) ?: 0)
        return sdf.format(date)
    }

    fun formatDayTime(dt: Long): String {
        val date = Date(dt * 1000L)
        val formatter = SimpleDateFormat("hh:mm aa", Locale(SettingsConstants.getLang()))
        return formatter.format(date)
    }

    fun getDate(dt: Int?):String {
        val date= Date(dt?.times(1000L) ?: 0)
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale(SettingsConstants.getLang()))
        return formatter.format(date)
    }
    fun getDateTimeAlert(dt:Long):String{
        val date = Date(dt)
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm aa")
        return sdf.format(date)
    }

    fun getDay(dt: Long?):String{
        val date= Date(dt?.times(1000L) ?: 0)
        val formatter = SimpleDateFormat("EEEE", Locale(SettingsConstants.getLang()))
        return formatter.format(date)
    }
    fun getHour(dt:Long):String{
        val date= Date(dt * 1000L)
        val sdf = SimpleDateFormat("hh aa",Locale(SettingsConstants.getLang()))
        return sdf.format(date)
    }

}