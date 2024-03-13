package com.example.weatherapp.Utilities


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
object Formatter {

    fun convertUnixToTime(unixSeconds: Long): String =
        formatDate(unixSeconds * 1000L, "hh:mm a")

    fun getSunriseAndSunset(sunrise: Long, sunset: Long): Pair<String, String> =
        Pair(convertUnixToTime(sunrise), convertUnixToTime(sunset))

    fun getFormattedHour(hour: Long?): String =
        formatDate(hour?.times(1000L) ?: 0, "h:mm a")

    fun formatDayTime(dt: Long): String =
        formatDate(dt * 1000L, "hh:mm aa")

    fun getDate(dt: Int?): String =
        formatDate(dt?.times(1000L) ?: 0, "dd-MM-yyyy")

    fun getDateTimeAlert(dt: Long): String =
        formatDate(dt, "dd-MM-yyyy hh:mm aa")

    fun getDay(dt: Long?): String =
        formatDate(dt?.times(1000L) ?: 0, "EEEE")

    fun getHour(dt: Long): String =
        formatDate(dt * 1000L, "hh aa")

    private fun formatDate(milliseconds: Long, format: String): String {
        val date = Date(milliseconds)
        val formatter = SimpleDateFormat(format, Locale(SettingsConstants.getLangCode()))
        return formatter.format(date)
    }
}
