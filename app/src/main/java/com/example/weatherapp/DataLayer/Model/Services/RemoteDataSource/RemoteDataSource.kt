package com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource

import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        lang:String,
        units:String ) : Flow<WeatherResponse>
}