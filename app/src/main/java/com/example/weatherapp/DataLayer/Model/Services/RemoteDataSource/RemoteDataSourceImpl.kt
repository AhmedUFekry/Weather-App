package com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource

import com.example.weatherapp.Utilities.Constants
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl private constructor(private val service: WeatherWebService): RemoteDataSource {
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


    override fun getCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        units: String
    ) = flow {
        emit(service.getWeather(lat, lon, Constants.API_KEY, lang, units))
    }
}