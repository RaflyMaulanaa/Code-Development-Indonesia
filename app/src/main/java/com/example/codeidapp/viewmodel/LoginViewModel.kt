package com.example.codeidapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.codeidapp.data.WeatherRepository
import com.example.codeidapp.data.source.local.model.User

class LoginViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun login(username: String, password: String): Boolean {
        return weatherRepository.login(username, password)
    }

    fun preffiledAccount(username: String, password: String) {
        weatherRepository.prefiledAccount(username, password)
    }

//    fun saveUser(user: User): Boolean { }

    fun getUser(): User? {
        return weatherRepository.getUser()
    }


}