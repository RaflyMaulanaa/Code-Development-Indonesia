package com.example.codeidapp.viewmodel
import androidx.lifecycle.ViewModel
import com.example.codeidapp.data.WeatherRepository

class ProfileViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun updateProfile(newUsername: String, newPassword: String): Boolean {
        return weatherRepository.saveUser(newUsername, newPassword)
    }

}