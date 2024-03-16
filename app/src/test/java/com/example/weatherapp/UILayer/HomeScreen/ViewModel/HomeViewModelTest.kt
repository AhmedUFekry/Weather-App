package com.example.weatherapp.UILayer.HomeScreen.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.data.source.FakeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private lateinit var viewModel: HomeViewModel
    private lateinit var repo : FakeRepo
    private var lat : Double = 0.0
    private var lon : Double = 0.0

    @Before
    fun setUp(){
        lat = 25.2203776
        lon = 45.8296832
        repo = FakeRepo()
        viewModel = HomeViewModel(repo)
    }
    @Test
    fun getCurrentWeatherfor_Success() = runBlockingTest   {

        // When
        viewModel.getCurrentWeatherfor(lat, lon)


        // Then
        val result = viewModel.weatherStateFlow.value
        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.notNullValue()))
    }


    @Test
    fun getFavoriteWeather_Success() = runBlockingTest   {

        // When
        viewModel.getFavoriteWeather(lat, lon)


        // Then
        val result = viewModel.weatherStateFlow.value
        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.notNullValue()))
    }

}