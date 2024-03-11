package com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource

import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import com.example.weatherapp.Utilities.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherWebService {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId:String = Constants.API_KEY,
        @Query("lang") lang:String,
        @Query("units") units:String ): WeatherResponse
}