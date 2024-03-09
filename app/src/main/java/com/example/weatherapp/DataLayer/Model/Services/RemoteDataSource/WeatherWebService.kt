package com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource

import com.example.weatherapp.DataLayer.Model.DataModels.WeatherDto
import com.example.weatherapp.Utilities.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherWebService {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat :String,
        @Query("lon") lon:String,
        @Query("appid")appID:String = Constants.API_KEY,
        @Query("exclude") exclude:String = Constants.EXCLUDE,
        @Query("units") units:String = Constants.UNITS): WeatherDto
}