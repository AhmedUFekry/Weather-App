package com.example.weatherforecast.sharedprefernces

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapp.Utilities.SettingsConstants

class SharedPreferencesHelper constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    companion object {
        private const val PREF_KEY_DIALOG_SHOWN = "dialog_shown"
        private const val PREF_KEY_LOCATION = "location"
        private const val PREF_KEY_TEMPERATURE = "temperature"
        private const val PREF_KEY_LANG = "lang"
        private const val PREF_KEY_WIND_SPEED = "wSpeed"
        private const val PREF_KEY_NOTIFICATION_STATE = "notificationState"
        private const val PREF_KEY_NEW_SETTING = "isNewSetting"

        @Volatile
        private var instance: SharedPreferencesHelper? = null

        fun getInstance(context: Context): SharedPreferencesHelper {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferencesHelper(context.applicationContext).also { instance = it }
            }
        }
    }

    fun setDialogShown(shown: Boolean) {
        sharedPreferences.edit().putBoolean(PREF_KEY_DIALOG_SHOWN, shown).apply()
    }

    fun isDialogShown(): Boolean {
        return sharedPreferences.getBoolean(PREF_KEY_DIALOG_SHOWN, false)
    }

    fun saveCurrentLocation(key: String, value: Double?) {
        sharedPreferences.edit().putString(key, value.toString()).apply()
    }

    fun loadCurrentLocation(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun insertInData() {
        val editor = sharedPreferences.edit()
        SettingsConstants.location?.let { editor.putInt(PREF_KEY_LOCATION, if (it == SettingsConstants.Location.GPS) 0 else 1) }
        SettingsConstants.temperature?.let {
            editor.putInt(PREF_KEY_TEMPERATURE, when (it) {
                SettingsConstants.Temperature.C -> 0
                SettingsConstants.Temperature.K -> 1
                else -> 2
            })
        }
        SettingsConstants.lang?.let { editor.putInt(PREF_KEY_LANG, if (it == SettingsConstants.Lang.AR) 0 else 1) }
        SettingsConstants.windSpeed?.let {
            editor.putInt(PREF_KEY_WIND_SPEED, if (it == SettingsConstants.WindSpeed.M_S) 0 else 1)
        }
        SettingsConstants.notificationState?.let {
            editor.putInt(PREF_KEY_NOTIFICATION_STATE, if (it == SettingsConstants.NotificationState.OFF) 0 else 1)
        }
        editor.apply()
    }

    fun loadData() {
        sharedPreferences.apply {
            SettingsConstants.location =
                if (getInt(PREF_KEY_LOCATION, -1) == 0) SettingsConstants.Location.GPS else SettingsConstants.Location.MAP
            SettingsConstants.lang =
                if (getInt(PREF_KEY_LANG, -1) == 0) SettingsConstants.Lang.AR else SettingsConstants.Lang.EN
            SettingsConstants.windSpeed =
                if (getInt(PREF_KEY_WIND_SPEED, -1) == 0) SettingsConstants.WindSpeed.M_S else SettingsConstants.WindSpeed.MILE_HOUR
            SettingsConstants.temperature = when (getInt(PREF_KEY_TEMPERATURE, -1)) {
                0 -> SettingsConstants.Temperature.C
                1 -> SettingsConstants.Temperature.K
                else -> SettingsConstants.Temperature.F
            }
            SettingsConstants.notificationState =
                if (getInt(PREF_KEY_NOTIFICATION_STATE, -1) == 0) SettingsConstants.NotificationState.OFF else SettingsConstants.NotificationState.ON
        }
    }

    fun isNewSettingsRestart(): Int {
        return sharedPreferences.getInt(PREF_KEY_NEW_SETTING, -1)
    }

    fun saveAsNewSetting(num: Int) {
        sharedPreferences.edit().putInt(PREF_KEY_NEW_SETTING, num).apply()
    }
}
