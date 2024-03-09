package com.example.weatherapp.DataLayer.Model.Services.Repository

import com.example.weatherapp.DataLayer.Model.DataModels.WeatherDto
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    suspend fun getWeatherData(lat :String, lon:String): Flow<WeatherDto>

}