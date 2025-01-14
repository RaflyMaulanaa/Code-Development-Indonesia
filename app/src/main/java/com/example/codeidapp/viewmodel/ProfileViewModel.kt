package com.example.codeidapp.viewmodel
import androidx.lifecycle.ViewModel
import com.example.codeidapp.data.WeatherRepository

class ProfileViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun updateProfile(newUsername: String, newPassword: String): Boolean {
        val user = weatherRepository.getUser()
        return if (user != null) {
            // Memperbarui profil jika pengguna terdaftar
            weatherRepository.updateUser(user.username, newUsername, newPassword)
        } else {
            false
        }
    }

}