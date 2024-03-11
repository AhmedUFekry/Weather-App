package com.example.weatherapp.DataLayer.Model.Services.LocalDataSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.DataLayer.Model.DataModels.FaviourateLocationDto
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM fav_table")
    fun getAllLocation(): Flow<List<FaviourateLocationDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocation(location: FaviourateLocationDto)

    @Query("DELETE FROM fav_table")
    fun deleteAllProduct()

    @Delete
    fun delete(location: FaviourateLocationDto)

}