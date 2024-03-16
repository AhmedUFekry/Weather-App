package com.example.weatherapp.Utilities


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
object Formatter {


    fun getFormattedHour(hour: Long?): String =
        formatDate(hour?.times(1000L) ?: 0, "h:mm a")



    fun getDate(dt: Int?): String =
        formatDate(dt?.times(1000L) ?: 0, "dd-MM-yyyy")



    fun getDay(dt: Long?): String =
        formatDate(dt?.times(1000L) ?: 0, "EEEE")



    private fun formatDate(milliseconds: Long, format: String): String {
        val date = Date(milliseconds)
        val formatter = SimpleDateFormat(format, Locale(SettingsConstants.getLangCode()))
        return formatter.format(date)
    }
}
