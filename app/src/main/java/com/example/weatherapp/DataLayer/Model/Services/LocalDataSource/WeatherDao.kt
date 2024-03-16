package com.example.weatherapp.DataLayer.Model.Services.LocalDataSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM fav_table")
    fun getAllLocation(): Flow<List<FavouriteLocationDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocation(location: FavouriteLocationDto)

    @Query("DELETE FROM fav_table")
    fun deleteAllProduct()

    @Delete
    fun delete(location: FavouriteLocationDto)

}