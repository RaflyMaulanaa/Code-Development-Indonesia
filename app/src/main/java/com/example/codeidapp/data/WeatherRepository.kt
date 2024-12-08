package com.example.codeidapp.data

import android.util.Log
import com.example.codeidapp.data.source.local.entity.WeatherEntity
import com.example.codeidapp.data.source.local.preferences.UserPreferenceManager
import com.example.codeidapp.data.source.local.room.WeatherDao
import com.example.codeidapp.data.source.remote.network.ApiService
import com.example.codeidapp.data.source.local.model.User
import com.example.codeidapp.data.source.remote.response.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class WeatherRepository constructor(
    private val apiService: ApiService,
    private val userDao: WeatherDao,
    private val preferenceManager: UserPreferenceManager
) {

    fun login(username: String, password: String): Boolean {
        return preferenceManager.login(username, password)
    }

    fun prefiledAccount(username: String, password: String) {
        preferenceManager.saveUser(username, password)
    }

    fun saveUser(newUsername: String, newPassword: String): Boolean {
        return preferenceManager.saveUser(newUsername, newPassword)
    }

    fun getUser(): User? {
        return preferenceManager.getUser()
    }

    fun getWeatherDataAndSaveCity(
        latitude: String,
        longitude: String,
        appId: String
    ): Flow<Result<WeatherResponse>> = flow {
        emit(Result.Loading)
        try {
            val response =
                apiService.getWeather(latitude = latitude, longitude = longitude, appId = appId)

            saveCityNameToLocal(response)

            emit(Result.Success(response))

        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error mengambil data cuaca: ${e.message}")
            emit(Result.Error(e.message ?: "Terjadi kesalahan"))
        }
    }.onStart {
        // Optionally, perform actions when the flow starts (already covered with emit(Result.Loading))
    }.catch { e ->
        emit(Result.Error(e.message ?: "Terjadi kesalahan"))
    }

    private suspend fun saveCityNameToLocal(response: WeatherResponse) {
        try {
            val weatherEntity = WeatherEntity(
                id = response.id,
                cityName = response.name
            )
            // Save city name into the local database
            userDao.insertCity(weatherEntity)
        } catch (e: Exception) {
            Log.d("weatherData", "data: ${e.message.toString()} ")
        }

    }

    companion object {
        @Volatile
        private var instance: WeatherRepository? = null
        fun getInstance(
            apiService: ApiService,
            weatherDao: WeatherDao,
            preferenceManager: UserPreferenceManager
        ): WeatherRepository =
            instance ?: synchronized(this) {
                instance ?: WeatherRepository(apiService, weatherDao, preferenceManager)
            }.also { instance = it }
    }

}