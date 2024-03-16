package com.example.weatherapp.data.source

import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : WeatherLocalDataSource {

    private val faviourateLocation : MutableList<FavouriteLocationDto> = mutableListOf()

    override suspend fun insertLocation(location: FavouriteLocationDto) {
        if(!faviourateLocation.contains(location)){
            faviourateLocation.add(location)
        }
    }

    override suspend fun deleteLocation(location: FavouriteLocationDto) {
        faviourateLocation.remove(location)
    }

    override suspend fun getAllLocation()= flow {
        emit(faviourateLocation)
    }
}