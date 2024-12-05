package com.example.codeidapp.di

import android.content.Context
import com.example.codeidapp.data.WeatherRepository
import com.example.codeidapp.data.source.local.preferences.UserPreferenceManager
import com.example.codeidapp.data.source.local.room.WeatherDatabase
import com.example.codeidapp.data.source.remote.network.ApiConfig

object Injection {

    fun provideRepository(context: Context): WeatherRepository {
        val apiService = ApiConfig.getApiService()
        val database = WeatherDatabase.getInstance(context)
        val dao = database.weatherDao()
        val preferenceManager = UserPreferenceManager(context)
        return WeatherRepository.getInstance(apiService, dao, preferenceManager)
    }

}
