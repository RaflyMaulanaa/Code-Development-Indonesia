package com.example.codeidapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.codeidapp.databinding.FragmentHomeBinding
import com.example.codeidapp.viewmodel.HomeViewModel
import com.example.codeidapp.data.Result
import com.example.codeidapp.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireContext())
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupObserver ()

        binding.searchBarIv.setOnClickListener {
            val cityName = binding.cityEt.text.toString().trim()
            if (cityName.isNotEmpty()) {
                viewModel.getWeather(cityName)
            } else {
                Toast.makeText(context, "Masukkan nama kota terlebih dahulu Bosku", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun setupObserver (){
        viewModel.weatherData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Toast.makeText(context, "Memuat data cuaca bosku", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    val data = result.data
                    val conditionIcon = viewModel.getWeatherIcon(data.weather[0].description)
                    binding.conditionIv.setImageResource(conditionIcon)

                    binding.nameTv.text = data.name
                    binding.tempTv.text = "${data.main.temp}°C"
                    binding.minTempTv.text = "${data.main.temp_min}°C"
                    binding.maxTempTv.text = "${data.main.temp_max}°C"
                    binding.pressureTv.text = "${data.main.pressure} hPa"
                    binding.humidityTv.text = "${data.main.humidity}%"
                    binding.windTv.text = "${data.wind.speed} m/s"
                    binding.conditionDescTv.text = data.weather[0].description.capitalize()
                    binding.updatedAtTv.text = "Diperbarui pada: ${data.dt}"
                }
                is Result.Error -> {
                    Toast.makeText(context, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}