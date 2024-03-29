package com.example.weatherapp.DataLayer.Model.DataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Current(
	val sunrise: Int? = null,
	val temp: Double? = null,
	val visibility: Int? = null,
	val uvi: Any? = null,
	val pressure: Int? = null,
	val clouds: Int? = null,
	val feelsLike: Any? = null,
	val windGust: Any? = null,
	val dt: Int? = null,
	val windDeg: Int? = null,
	val dewPoint: Any? = null,
	val sunset: Int? = null,
	val weather: List<WeatherItem?>? = null,
	val humidity: Int? = null,
	val windSpeed: Int? = null
)
