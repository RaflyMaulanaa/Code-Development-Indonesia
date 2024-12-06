package com.example.codeidapp.data.source.remote.network

import com.example.codeidapp.data.source.remote.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "id",
        @Query("appid") appId: String
    ): WeatherResponse
}