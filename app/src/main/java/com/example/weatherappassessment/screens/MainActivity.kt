package com.example.weatherappassessment.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
//import androidx.
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherappassessment.dataExceptionHandling.DataException
import com.example.weatherappassessment.models.WeatherResponse
import com.example.weatherappassessment.ui.theme.WeatherAppAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppAssessmentTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

    val mainViewModel : MainViewModel = hiltViewModel()
    val weatherData = produceState<DataException<WeatherResponse,Boolean,Exception>>(
        initialValue = DataException(loading = true)) {
        value = mainViewModel.getWeatherData("-26.1219006",lon = "28.1198253")
    }.value


   if(weatherData.loading == true){
       Log.e("","========================== loading" )
   } else if(weatherData.data != null){
        Log.e("","========================== data  = ${weatherData.data}")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppAssessmentTheme {
        Greeting("Android")
    }
}