package com.example.weatherappassessment.dao

import androidx.compose.ui.unit.Constraints
import com.example.weatherappassessment.networkService.WeatherApi
import com.example.weatherappassessment.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Singleton
    @Provides
    fun provideWeather():WeatherApi{
        return Retrofit.Builder().baseUrl(
            Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherApi::class.java)
    }
}