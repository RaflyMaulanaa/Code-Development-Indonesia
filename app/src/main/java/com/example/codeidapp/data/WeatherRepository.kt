package com.example.codeidapp.data

import com.example.codeidapp.data.source.local.preferences.UserPreferenceManager
import com.example.codeidapp.data.source.local.room.WeatherDao
import com.example.codeidapp.data.source.remote.network.ApiService
import com.example.codeidapp.data.source.local.model.User

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

//    fun saveUser(user: User): Boolean {
//        return preferenceManager.saveUser(user)
//    }

    fun getUser(): User? {
        return preferenceManager.getUser()
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