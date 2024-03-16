package com.example.weatherapp.data.source

import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteDataSource : RemoteDataSource{
    override fun getCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        units: String,
    )= flow {
        emit(WeatherResponse(lon = lon , lat = lat ))
    }
}