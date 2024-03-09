package com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource

import com.example.weatherapp.DataLayer.Model.DataModels.WeatherDto
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getWeather(lat :String, lon:String): Flow<WeatherDto>
}