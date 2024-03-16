package com.example.weatherapp.DataLayer.Model.Services.LocalDataSource

import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {

   suspend fun insertLocation(location: FavouriteLocationDto)

    suspend fun deleteLocation(location: FavouriteLocationDto)

    suspend fun getAllLocation(): Flow<List<FavouriteLocationDto>>
}