package com.example.weatherapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.LocationKey
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherDatabase
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSource
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSourceImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocalDataSourceTese {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var localSource: WeatherLocalDataSource

    @Before
    fun setup(){
        weatherDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        localSource = WeatherLocalDataSourceImpl(weatherDatabase.getWeather())
    }

    @After
    fun tearDown(){
        weatherDatabase.close()
    }

    // insert Favorite Location test
    @Test
    fun insertWeatherLocally() = runBlockingTest{
        //Given
        val favLocation = FavouriteLocationDto(LocationKey(29.3059751, 30.8549351), "Fayoum, Egypt", "24")
        //when
        localSource.insertLocation(favLocation)

        // Then
        val result = localSource.getAllLocation().first()
        MatcherAssert.assertThat(result[0].locationKey, CoreMatchers.`is`(favLocation.locationKey))
    }

    // delete Favorite Location test
    @Test
    fun deleteWeatherFromLocal() = runBlockingTest{
        //Given
        val favLocation = FavouriteLocationDto(LocationKey(25.2203776, 45.8296832), "Suez, Egypt", "13")
        localSource.insertLocation(favLocation)

        //when
        localSource.deleteLocation(favLocation)

        // Then
        val result = localSource.getAllLocation()
        MatcherAssert.assertThat(result.first().isEmpty(), CoreMatchers.`is`(true))
    }

    @Test
    fun getAllFavWeather() = runBlockingTest {
        // Given
        val favLocation1 = FavouriteLocationDto(LocationKey(25.2203776, 45.8296832), "Suez, Egypt", "13")
        val favLocation2 = FavouriteLocationDto(LocationKey(27.2203776, 12.8296832), "Cairo, Egypt", "19")
        val favLocation3 = FavouriteLocationDto(LocationKey(21.2203776, 62.8296832), "Ismailia, Egypt", "20")
        val favLocation4 = FavouriteLocationDto(LocationKey(28.2203776, 22.8296832), "Alexandria, Egypt", "10")

        localSource.insertLocation(favLocation1)
        localSource.insertLocation(favLocation2)
        localSource.insertLocation(favLocation3)
        localSource.insertLocation(favLocation4)

        // When
        val result = localSource.getAllLocation().first().size

        // Then
        MatcherAssert.assertThat(result, CoreMatchers.`is`(4)) // Ensure it matches the number of inserted locations

    }



}