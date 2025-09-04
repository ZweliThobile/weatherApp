package com.example.weatherappassessment.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
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
import androidx.compose.ui.platform.LocalContext
//import androidx.
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherappassessment.dataExceptionHandling.DataException
import com.example.weatherappassessment.models.WeatherResponse
import com.example.weatherappassessment.ui.theme.WeatherAppAssessmentTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


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
                    Greeting("Thobile")
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


    val context = LocalContext.current
    val mainViewModel: MainViewModel = hiltViewModel()



    val weatherData = produceState<DataException<WeatherResponse, Boolean, Exception>>(
        initialValue = DataException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(
            lat = getLocation(context)?.latitude.toString(),
            lon = getLocation(context)?.longitude.toString()
        )
    }.value


    if (weatherData.loading == true) {
        Log.e("", "========================== loading")
    } else if (weatherData.data != null) {
        Log.e("", "========================== data  = ${weatherData.data}")
    }
}


suspend fun getLocation(context: Context): Location? {

    var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1001
        )
        return null

    }


    return suspendCancellableCoroutine { cont ->
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            cont.resume(location)
        }.addOnFailureListener {
            cont.resume(null)
        }
    }
}


@Composable
fun getWeather(lat: String, lon: String) {


    val mainViewModel: MainViewModel = hiltViewModel()
    val weatherData = produceState<DataException<WeatherResponse, Boolean, Exception>>(
        initialValue = DataException(loading = true)
    ) {
        value = mainViewModel.getWeatherData("-26.1219006", lon = "28.1198253")
    }.value


    if (weatherData.loading == true) {
        Log.e("", "========================== loading")
    } else if (weatherData.data != null) {
        Log.e("", "========================== data  = ${weatherData.data}")
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppAssessmentTheme {
        Greeting("Android")
    }
}