package com.example.weatherapp.UILayer.FavouritsScreen.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.LocationKey
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
class FavouriteViewModelTest{


    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavouriteViewModel
    private lateinit var repo : FakeRepo
    lateinit var favLocation : FavouriteLocationDto

    @Before
    fun setUp(){
        favLocation = FavouriteLocationDto(LocationKey(25.2203776, 45.8296832), "Suez, Egypt", "13")
        repo = FakeRepo()
        viewModel = FavouriteViewModel(repo)
    }


    @Test
    fun getSavedLocations() = runBlockingTest {
        // Given
        val locationList = listOf(
            FavouriteLocationDto(LocationKey(25.2203776, 45.8296832), "Suez, Egypt", "13"),
            FavouriteLocationDto(LocationKey(25.2203776, 45.8296832), "Suez, Egypt", "13")
        )
        locationList.forEach{
            viewModel.insertLocation(it)
        }

        // When
        viewModel.getSavedLocations()

        // Then
        val result = viewModel.locationList.value
        MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
    }

    @Test
    fun insertLocation() = runBlockingTest{

        //when
        viewModel.insertLocation(favLocation)

        // Then
        val result = viewModel.locationList.value
        MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
    }




    @Test
    fun deleteLocation(){
        //Given
        viewModel.insertLocation(favLocation)

        //when
        viewModel.deleteLocation(favLocation)

        // Then
        val result = viewModel.locationList.value
        MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
    }

}