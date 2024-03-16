package com.example.weatherapp.DataLayer.Model.Services.Repository

import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSource
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSourceImpl
import com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource.RemoteDataSource
import com.example.weatherapp.Utilities.SettingsConstants
import kotlinx.coroutines.flow.Flow

class WeatherRepoImpl (
    private val weatherRemoteDataSource: RemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
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
        return weatherRemoteDataSource.getCurrentWeather(lat, lon, lang,unit)
    }

    override suspend fun getLocalLocation(): Flow<List<FavouriteLocationDto>> {
        return weatherLocalDataSource.getAllLocation()
    }

    override suspend fun insertLocation(location: FavouriteLocationDto) {
        weatherLocalDataSource.insertLocation(location)
    }

    override suspend fun deleteLocation(location: FavouriteLocationDto) {
        weatherLocalDataSource.deleteLocation(location)
    }


}