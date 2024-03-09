package com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource

import com.example.weatherapp.DataLayer.Model.DataModels.WeatherDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl private constructor(private val service: WeatherWebService): RemoteDataSource{
    companion object {
        private var instance: RemoteDataSourceImpl? = null

        fun getInstance(): RemoteDataSourceImpl {
            return instance ?: synchronized(this) {
                val retrofitService = RetrofitHelper.service
                val tempInstance = RemoteDataSourceImpl(retrofitService)
                instance = tempInstance
                tempInstance
            }
        }
    }


    override suspend fun getWeather(lat: String, lon: String): Flow<WeatherDto> = flow{
        emit(service.getWeather(lat, lon))
    }
}