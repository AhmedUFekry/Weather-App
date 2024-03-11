package com.example.weatherapp.DataLayer.Model.Services.LocalDataSource

import android.content.Context
import com.example.weatherapp.DataLayer.Model.DataModels.FaviourateLocationDto
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSource private constructor(private val dao: WeatherDao):
    IWeatherLocalDataSource {

    companion object {
        private var instance: WeatherLocalDataSource? = null

        fun getInstance(context: Context?): WeatherLocalDataSource {
            return instance ?: synchronized(this) {
                val database = WeatherDatabase.getInstance(context!!)
                val dao = database.getWeather()
                val tempInstance = WeatherLocalDataSource(dao)
                instance = tempInstance
                tempInstance
            }
        }
    }

    override suspend fun insertLocation(location: FaviourateLocationDto) {
        dao.insertLocation(location)
    }

    override suspend fun deleteLocation(location: FaviourateLocationDto) {
        dao.delete(location)
    }

    override suspend fun getAllLocation(): Flow<List<FaviourateLocationDto>> {
        return dao.getAllLocation()
    }


}