package com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource

import com.example.weatherapp.Utilities.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val retrofitInstance= Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : WeatherWebService by lazy {
        retrofitInstance.create(WeatherWebService::class.java)
    }
}