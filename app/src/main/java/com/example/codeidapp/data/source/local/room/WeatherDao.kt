package com.example.codeidapp.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codeidapp.data.source.local.entity.WeatherEntity

@Dao
interface WeatherDao {

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertUser(user: List<WeatherEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(weather: WeatherEntity)

    @Query("SELECT * FROM weather_data WHERE id = :id")
    suspend fun getWeatherById(id: Int): WeatherEntity?

}