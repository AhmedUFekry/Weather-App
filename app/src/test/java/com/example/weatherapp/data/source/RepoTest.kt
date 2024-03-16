package com.example.weatherapp.data.source

import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.LocationKey
import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepo
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepoImpl
import com.example.weatherapp.data.source.FakeLocalDataSource
import com.example.weatherapp.data.source.FakeRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

class RepoTest {
    private lateinit var weatherRemoteDataSource: FakeRemoteDataSource
    private lateinit var weatherLocalDataSource: FakeLocalDataSource
    private lateinit var weatherRepository: WeatherRepo
    lateinit var  favLocation : FavouriteLocationDto

    @Before
    fun setUp() {
        weatherRemoteDataSource = FakeRemoteDataSource()
        weatherLocalDataSource = FakeLocalDataSource()
        weatherRepository = WeatherRepoImpl(weatherRemoteDataSource, weatherLocalDataSource)
        favLocation = FavouriteLocationDto(LocationKey(25.2203776, 45.8296832), "Suez, Egypt", "13")
    }

    @Test
    fun getCurrentWeatherFor() = runBlockingTest {

        // When
        val result = weatherRepository.getCurrentWeatherfor(favLocation.locationKey.lat, favLocation.locationKey.long)

        // Then
        val expectedResult = WeatherResponse(lat = favLocation.locationKey.lat, lon = favLocation.locationKey.long)
        result.collect {
            MatcherAssert.assertThat(it, IsEqual(expectedResult))
        }
    }

    @Test
    fun insertFavWeatherInToDataBase() = runBlockingTest{

        //when
        weatherRepository.insertLocation(favLocation)

        // Then
        val result = weatherRepository.getLocalLocation().first()
        MatcherAssert.assertThat(result[0].locationKey, CoreMatchers.`is`(favLocation.locationKey))
    }



    @Test
    fun deleteFavWeather() = runBlockingTest{
        //Given
        weatherRepository.insertLocation(favLocation)

        //when
        weatherRepository.deleteLocation(favLocation)

        // Then
        val result = weatherRepository.getLocalLocation()
        MatcherAssert.assertThat(result.first().isEmpty(), CoreMatchers.`is`(true))
    }
}