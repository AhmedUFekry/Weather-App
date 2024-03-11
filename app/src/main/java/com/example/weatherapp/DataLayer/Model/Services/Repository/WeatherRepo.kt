package com.example.weatherapp.DataLayer.Model.Services.Repository


import com.example.weatherapp.DataLayer.Model.DataModels.FaviourateLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    suspend fun getCurrentWeather(): Flow<WeatherResponse>
    suspend fun getLocalAllLocation() : Flow<List<FaviourateLocationDto>>
    suspend fun insertLocation(location: FaviourateLocationDto)
    suspend fun deleteLocation(location: FaviourateLocationDto)

    suspend fun getFavouriteWeather(lat: Double,
                                    lon: Double,
                                    lang: String,
                                    units: String): Flow<WeatherResponse>
}