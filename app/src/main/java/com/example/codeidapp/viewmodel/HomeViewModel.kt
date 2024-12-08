package com.example.codeidapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codeidapp.BuildConfig
import com.example.codeidapp.R
import com.example.codeidapp.data.WeatherRepository
import com.example.codeidapp.data.source.remote.response.WeatherResponse
import kotlinx.coroutines.launch
import com.example.codeidapp.data.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherData = MutableStateFlow<Result<WeatherResponse>>(Result.Loading)
    val weatherData: StateFlow<Result<WeatherResponse>> = _weatherData

    fun getWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            weatherRepository.getWeatherDataAndSaveCity(latitude, longitude, BuildConfig.OPEN_WEATHER_API_KEY)
                .collect { result ->
                    _weatherData.value = result
                }
        }
    }
    fun getWeatherIcon(description: String): Int {
        return when {
            "clouds" in description.lowercase() -> R.drawable.broken_clouds
            "clear" in description.lowercase() -> R.drawable.clear_day
            "rain" in description.lowercase() -> R.drawable.rain
            else -> R.drawable.scattered_clouds
        }
    }
}