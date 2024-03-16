package com.example.weatherapp.UILayer.FavouritsScreen.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
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

    private val _locationList = MutableStateFlow<ApiState<List<FavouriteLocationDto>>>(ApiState.Loading<List<FavouriteLocationDto>>())
    val locationList : StateFlow<ApiState<List<FavouriteLocationDto>>> = _locationList

    init {
        getSavedLocations()
    }

    fun getSavedLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepo.getLocalLocation()
                .catch { e -> _locationList.value = ApiState.Failed(e) }
                .collect{
                    _locationList.value = ApiState.Success(it)
                }
        }
    }

    fun deleteLocation(location: FavouriteLocationDto) {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepo.deleteLocation(location)
            getSavedLocations()
        }
    }

    fun insertLocation(location: FavouriteLocationDto) {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepo.insertLocation(location)
        }
    }
}