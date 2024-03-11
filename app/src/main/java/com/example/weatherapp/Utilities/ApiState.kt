package com.example.weatherapp.Utilities

sealed class ApiState<T> {
    class Success<T>(val data: T) : ApiState<T>()
    class Failed<T>(val msg: Throwable) : ApiState<T>()
    class Loading<T> : ApiState<T>()
}