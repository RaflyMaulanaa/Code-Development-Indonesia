package com.example.codeidapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codeidapp.BuildConfig
import com.example.codeidapp.R
import com.example.codeidapp.data.WeatherRepository
import com.example.codeidapp.data.source.remote.response.WeatherResponse
import kotlinx.coroutines.launch
import com.example.codeidapp.data.Result

class HomeViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<Result<WeatherResponse>>()
    val weatherData: LiveData<Result<WeatherResponse>> = _weatherData

    fun getWeather(latitude: String, longitude: String) {
        _weatherData.value = Result.Loading
        viewModelScope.launch {
            val result = weatherRepository.getWeather(latitude,longitude,BuildConfig.OPEN_WEATHER_API_KEY)
            _weatherData.value = result
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