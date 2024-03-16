package com.example.weatherapp.UILayer.HomeScreen.ViewModel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepo
import com.example.weatherapp.Utilities.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val iWeatherRepo: WeatherRepo
) : ViewModel() {

    private val _weatherMutableStateFlow = MutableStateFlow<ApiState<WeatherResponse>>(ApiState.Loading<WeatherResponse>())

    val weatherStateFlow: StateFlow<ApiState<WeatherResponse>> = _weatherMutableStateFlow


    fun getFavoriteWeather(lat :Double, long: Double){
        viewModelScope.launch(Dispatchers.IO) {
            iWeatherRepo.getCurrentWeatherfor(lat,long)
                .catch { _weatherMutableStateFlow.value = ApiState.Failed(it) }
                .collect{
                    _weatherMutableStateFlow.value = ApiState.Success(it)
                }
        }
    }

    fun getCurrentWeatherfor(lat: Double,long: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            iWeatherRepo.getCurrentWeatherfor(lat,long)
                .catch { _weatherMutableStateFlow.value = ApiState.Failed(it) }
                .collect{
                    _weatherMutableStateFlow.value = ApiState.Success(it)
                }
        }
    }

}