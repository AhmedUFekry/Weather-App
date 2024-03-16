package com.example.weatherapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.LocationKey
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherDatabase: WeatherDatabase
    lateinit var favLocation : FavouriteLocationDto
    @Before
    fun setup(){
        weatherDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),WeatherDatabase::class.java).build()
        favLocation = FavouriteLocationDto(LocationKey(27.2203776, 33.8296832), "Suez, Egypt", "13")

    }

    @After
    fun tearDown(){
        weatherDatabase.close()
    }

    // insert Favorite Location test
    @Test
    fun insertWeatherLocally() = runBlockingTest{

        //when
        weatherDatabase.getWeather().insertLocation(favLocation)

        // Then
        val result = weatherDatabase.getWeather().getAllLocation().first()
        MatcherAssert.assertThat(result[0].locationKey, CoreMatchers.`is`(favLocation.locationKey))
    }


    // delete Favorite Location test
    @Test
    fun deleteWeatherFromLocal() = runBlockingTest{
        //Given
        weatherDatabase.getWeather().insertLocation(favLocation)

        //when
        weatherDatabase.getWeather().delete(favLocation)

        // Then
        val result = weatherDatabase.getWeather().getAllLocation()
        MatcherAssert.assertThat(result.first().isEmpty(), CoreMatchers.`is`(true))
    }

    // getAllFavWeather Location test
    @Test
    fun getAllFavWeather() = runBlockingTest {
        // Given
        val favLocation1 = FavouriteLocationDto(LocationKey(25.2203776, 45.8296832), "Suez, Egypt", "13")
        val favLocation2 = FavouriteLocationDto(LocationKey(27.2203776, 12.8296832), "Cairo, Egypt", "19")
        val favLocation3 = FavouriteLocationDto(LocationKey(21.2203776, 62.8296832), "Ismailia, Egypt", "20")
        val favLocation4 = FavouriteLocationDto(LocationKey(28.2203776, 22.8296832), "Alexandria, Egypt", "10")

        weatherDatabase.getWeather().insertLocation(favLocation1)
        weatherDatabase.getWeather().insertLocation(favLocation2)
        weatherDatabase.getWeather().insertLocation(favLocation3)
        weatherDatabase.getWeather().insertLocation(favLocation4)

        // When
        val result = weatherDatabase.getWeather().getAllLocation().first().size

        // Then
        MatcherAssert.assertThat(result, CoreMatchers.`is`(4))

    }



}