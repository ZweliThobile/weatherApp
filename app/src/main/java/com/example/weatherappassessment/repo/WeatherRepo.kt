package com.example.weatherappassessment.repo

import android.util.Log
import com.example.weatherappassessment.dataExceptionHandling.DataException
import com.example.weatherappassessment.models.WeatherResponse
import com.example.weatherappassessment.networkService.WeatherApi
import javax.inject.Inject

class WeatherRepo @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(lat : String, lon : String): DataException<WeatherResponse, Boolean, Exception>{

        val response = try {

            api.getWeatherForecast(lat, lon = lon)
        }catch (e: Exception){


            Log.e("APIresponse", " excpetion = ${e.toString()}")

            return DataException(e = e)

        }



        Log.e("APIresponse ", "data response = $response")
        return DataException(data = response)
    }
}