package com.example.weatherapp.Utilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepo
import com.example.weatherapp.UILayer.FavouritsScreen.ViewModel.FavouriteViewModel
import com.example.weatherapp.UILayer.HomeScreen.ViewModel.HomeViewModel

class ViewModelFactory(private val iWeatherRepo : WeatherRepo)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(iWeatherRepo) as T
        }else if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
                FavouriteViewModel(iWeatherRepo) as T
        }else{
            throw IllegalArgumentException("Class Not Found")
        }
    }
}