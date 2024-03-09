package com.example.weatherapp.DataLayer.Model.Services.Repository

import com.example.weatherapp.DataLayer.Model.DataModels.WeatherDto
import com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource.RemoteDataSourceImpl
import kotlinx.coroutines.flow.Flow

class WeatherRepoImpl private constructor(
    private val remoteDataSourceImp: RemoteDataSourceImpl) : WeatherRepo {

    companion object{
        private var instance : WeatherRepoImpl? = null
        fun getInstance(
            remoteDataSourceImp: RemoteDataSourceImpl,
        ): WeatherRepoImpl{
            return instance?: synchronized(this){
                val temp = WeatherRepoImpl(remoteDataSourceImp)
                instance = temp
                temp
            }
        }
    }

    override suspend fun getWeatherData(lat: String, lon: String): Flow<WeatherDto> {
        return remoteDataSourceImp.getWeather(lat,lon)
    }
}