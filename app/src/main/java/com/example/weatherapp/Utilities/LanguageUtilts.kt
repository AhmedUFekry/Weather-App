package com.example.weatherforecast.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

import java.util.*

object LanguageUtils {

    /*fun getArabicNumbers(number: String): String {
        val arabicNumberMap = mapOf(
            '0' to "٠",
            '1' to "١",
            '2' to "٢",
            '3' to "٣",
            '4' to "٤",
            '5' to "٥",
            '6' to "٦",
            '7' to "٧",
            '8' to "٨",
            '9' to "٩"
        )
        val arabicNumber = StringBuilder()
        for (element in number) {
            arabicNumber.append(arabicNumberMap[element] ?: "")
        }
        return arabicNumber.toString()
    }*/

    fun setAppLayoutDirections(locale: String, context: Context) {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLayoutDirection(Locale(locale))
        applyConfiguration(configuration, context)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun setAppLocale(localeCode: String, context: Context) {
        val resources = context.resources
        val dm = resources.displayMetrics
        val config = Configuration(resources.configuration)
        setLocaleCompat(config, Locale(localeCode))
        applyConfiguration(config, context)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun changeLang(context: Context, langCode: String): ContextWrapper {
        var updatedContext = context
        val sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
        if (langCode.isNotEmpty() && sysLocale.language != langCode) {
            val locale = Locale(langCode)
            Locale.setDefault(locale)
            val config = Configuration(context.resources.configuration)
            setLocaleCompat(config, locale)
            updatedContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context.createConfigurationContext(config)
            } else {
                applyConfiguration(config, context)
                context
            }
        }
        return ContextWrapper(updatedContext)
    }

    @Suppress("DEPRECATION")
    private fun setLocaleCompat(config: Configuration, locale: Locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }
    }

    private fun applyConfiguration(config: Configuration, context: Context) {
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
