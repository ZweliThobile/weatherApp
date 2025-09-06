package com.example.weatherappassessment.screens

import androidx.lifecycle.ViewModel
import com.example.weatherappassessment.dataExceptionHandling.DataException
import com.example.weatherappassessment.models.FiveDayWeatherResponse
import com.example.weatherappassessment.models.WeatherResponse
import com.example.weatherappassessment.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel(){

    suspend fun  getWeatherData(lat : String, lon : String) : DataException<WeatherResponse, Boolean, Exception> {
        return repo.getWeather(lat,lon)
    }

    suspend fun  getFiveDayWeatherForecast(lat : String, lon : String) : DataException<FiveDayWeatherResponse, Boolean, Exception> {
        return repo.getFiveDayWeather(lat,lon)
    }
}