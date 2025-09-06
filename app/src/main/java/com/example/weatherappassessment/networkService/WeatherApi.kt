package com.example.weatherappassessment.networkService

import com.example.weatherappassessment.models.FiveDayWeatherResponse
import com.example.weatherappassessment.models.WeatherResponse
import com.example.weatherappassessment.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherApi {


    //https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid={API key}



    @GET(value = "3.0/onecall?")
    suspend fun getWeatherForecast(
        @Query(value = "lat") lat : String,
        @Query(value = "lon") lon : String,
        @Query(value = "units") units : String = "metric",
        @Query(value = "appid") appid : String = Constants.API_KEY,
    ):WeatherResponse

    @GET(value = "2.5/forecast?")
    suspend fun getFiveDayWeatherForecast(
        @Query(value = "lat") lat : String,
        @Query(value = "lon") lon : String,
        @Query(value = "units") units : String = "metric",
        @Query(value = "appid") appid : String = Constants.API_KEY,
    ):FiveDayWeatherResponse
}