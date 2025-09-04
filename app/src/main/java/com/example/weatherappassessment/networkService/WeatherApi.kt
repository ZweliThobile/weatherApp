package com.example.weatherappassessment.networkService

import com.example.weatherappassessment.models.WeatherResponse
import com.example.weatherappassessment.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherApi {


    //https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid={API key}



    @GET(value = "onecall?")
    suspend fun  getWeatherForecast(
        @Query(value = "lat") lat : String,
        @Query(value = "lon") lon : String,
        @Query(value = "appid") appid : String = Constants.API_KEY,
    ):WeatherResponse


}