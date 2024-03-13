package com.example.weatherapp.DataLayer.Model.Services.LocalDataSource

import com.example.weatherapp.DataLayer.Model.DataModels.FaviourateLocationDto
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {

   suspend fun insertLocation(location: FaviourateLocationDto)

    suspend fun deleteLocation(location: FaviourateLocationDto)

    suspend fun getAllLocation(): Flow<List<FaviourateLocationDto>>
}