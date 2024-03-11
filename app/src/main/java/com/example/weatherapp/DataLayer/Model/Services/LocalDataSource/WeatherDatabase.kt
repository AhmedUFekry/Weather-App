package com.example.weatherapp.DataLayer.Model.Services.LocalDataSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.DataLayer.Model.DataModels.FaviourateLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.LocationKeyConverter


@Database(entities = [FaviourateLocationDto::class], version = 1)
@TypeConverters(LocationKeyConverter::class)
abstract class WeatherDatabase : RoomDatabase(){
    abstract fun getWeather() : WeatherDao
    companion object{
        @Volatile
        private var instance : WeatherDatabase? = null
        fun getInstance(context: Context) : WeatherDatabase {
            return instance ?: synchronized(this){
                val dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java, "weatherDb")
                    .build()
                instance = dbInstance
                dbInstance
            }
        }
    }
}