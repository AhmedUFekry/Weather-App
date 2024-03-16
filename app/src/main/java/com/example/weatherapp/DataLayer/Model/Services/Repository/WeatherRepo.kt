package com.example.weatherapp.DataLayer.Model.Services.Repository


import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    suspend fun getCurrentWeatherfor(lat: Double, lon: Double): Flow<WeatherResponse>

    suspend fun getLocalLocation() : Flow<List<FavouriteLocationDto>>
    suspend fun insertLocation(location: FavouriteLocationDto)
    suspend fun deleteLocation(location: FavouriteLocationDto)
    
}