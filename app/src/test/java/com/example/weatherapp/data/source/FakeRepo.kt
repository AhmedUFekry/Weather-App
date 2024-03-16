package com.example.weatherapp.data.source

import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSource
import com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource.RemoteDataSource
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepo
import com.example.weatherapp.Utilities.SettingsConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRepo : WeatherRepo {

    private  val fakeRemoteDataSource : RemoteDataSource = FakeRemoteDataSource()
    private val fakeLocalDataSource : WeatherLocalDataSource = FakeLocalDataSource()

    override suspend fun getCurrentWeatherfor(lat: Double, lon: Double): Flow<WeatherResponse> {
        val lang = SettingsConstants.getLangCode()
        val unit = "metric"
        return fakeRemoteDataSource.getCurrentWeather(lat, lon ,lang ,unit)
    }

    override suspend fun getLocalLocation(): Flow<List<FavouriteLocationDto>> {
        return fakeLocalDataSource.getAllLocation()
    }

    override suspend fun insertLocation(location: FavouriteLocationDto) {
        fakeLocalDataSource.insertLocation(location)
    }

    override suspend fun deleteLocation(location: FavouriteLocationDto) {
        fakeLocalDataSource.deleteLocation(location)
    }

}