package com.example.weatherapp.DataLayer.Model.Services.Repository

import android.content.Context
import android.util.Log
import com.example.weatherapp.DataLayer.Model.DataModels.FaviourateLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSourceImpl
import com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource.RemoteDataSource
import com.example.weatherapp.Utilities.SettingsConstants
import com.example.weatherforecast.sharedprefernces.SharedPreferencesHelper
import kotlinx.coroutines.flow.Flow

class WeatherRepoImpl private constructor(
    private val weatherRemoteDataSource: RemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSourceImpl
) : WeatherRepo {

    companion object{
        private var instance : WeatherRepoImpl? = null
        fun getInstance(
            weatherRemoteDataSource: RemoteDataSource,
            weatherLocalDataSource: WeatherLocalDataSourceImpl
        ): WeatherRepoImpl{
            return instance?: synchronized(this){
                val temp = WeatherRepoImpl(weatherRemoteDataSource, weatherLocalDataSource)
                instance = temp
                temp
            }
        }
    }

    override suspend fun getCurrentWeatherfor(lat: Double , lon: Double): Flow<WeatherResponse> {
        val lang = SettingsConstants.getLangCode()
        val unit = "metric"
        Log.i("TAG", "getCurrentWeatherFor: " + lat + lon)
        return weatherRemoteDataSource.getCurrentWeather(lat, lon, lang,unit)
    }

    override suspend fun getLocalAllLocation(): Flow<List<FaviourateLocationDto>> {
        return weatherLocalDataSource.getAllLocation()
    }

    override suspend fun insertLocation(location: FaviourateLocationDto) {
        weatherLocalDataSource.insertLocation(location)
    }

    override suspend fun deleteLocation(location: FaviourateLocationDto) {
        weatherLocalDataSource.deleteLocation(location)
    }

    override suspend fun getFavouriteWeather(
        lat: Double,
        lon: Double,
        lang: String,
        units: String
    ): Flow<WeatherResponse> {
        return weatherRemoteDataSource.getCurrentWeather(lat, lon, lang, units)
    }
}