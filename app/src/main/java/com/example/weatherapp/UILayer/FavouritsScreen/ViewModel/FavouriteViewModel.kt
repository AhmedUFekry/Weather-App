package com.example.weatherapp.UILayer.FavouritsScreen.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.DataLayer.Model.DataModels.FaviourateLocationDto
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepo
import com.example.weatherapp.Utilities.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val WeatherRepo : WeatherRepo,
) : ViewModel() {

    private val _locationList = MutableStateFlow<ApiState<List<FaviourateLocationDto>>>(ApiState.Loading<List<FaviourateLocationDto>>())
    val locationList : StateFlow<ApiState<List<FaviourateLocationDto>>> = _locationList

    init {
        getSavedLocations()
    }

    private fun getSavedLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepo.getLocalAllLocation()
                .catch { e -> _locationList.value = ApiState.Failed(e) }
                .collect{
                    _locationList.value = ApiState.Success(it)
                }
        }
    }

    fun deleteLocation(location: FaviourateLocationDto) {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepo.deleteLocation(location)
            getSavedLocations()
        }
    }

    fun insertLocation(location: FaviourateLocationDto) {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepo.insertLocation(location)
        }
    }
}