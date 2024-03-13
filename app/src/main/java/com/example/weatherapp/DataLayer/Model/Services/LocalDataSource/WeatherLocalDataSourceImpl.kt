package com.example.weatherapp.DataLayer.Model.Services.LocalDataSource

import android.content.Context
import com.example.weatherapp.DataLayer.Model.DataModels.FaviourateLocationDto
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSourceImpl private constructor(private val dao: WeatherDao):
    WeatherLocalDataSource {

    companion object {
        private var instance: WeatherLocalDataSourceImpl? = null

        fun getInstance(context: Context?): WeatherLocalDataSourceImpl {
            return instance ?: synchronized(this) {
                val database = WeatherDatabase.getInstance(context!!)
                val dao = database.getWeather()
                val tempInstance = WeatherLocalDataSourceImpl(dao)
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