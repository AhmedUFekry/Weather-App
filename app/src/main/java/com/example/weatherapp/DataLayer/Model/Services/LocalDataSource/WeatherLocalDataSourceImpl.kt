package com.example.weatherapp.DataLayer.Model.Services.LocalDataSource

import android.content.Context
import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSourceImpl(private val dao: WeatherDao):
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

    override suspend fun insertLocation(location: FavouriteLocationDto) {
        dao.insertLocation(location)
    }

    override suspend fun deleteLocation(location: FavouriteLocationDto) {
        dao.delete(location)
    }

    override suspend fun getAllLocation(): Flow<List<FavouriteLocationDto>> {
        return dao.getAllLocation()
    }


}