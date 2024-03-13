package com.example.weatherapp.UILayer.HomeScreen.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepo

class HomeViewModelFactory(private val iWeatherRepo : WeatherRepo)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(iWeatherRepo) as T
        }else{
            throw IllegalArgumentException("Class Not Found")
        }
    }
}