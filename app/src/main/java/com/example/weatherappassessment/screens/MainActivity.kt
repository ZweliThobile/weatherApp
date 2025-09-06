package com.example.weatherappassessment.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherappassessment.R
import com.example.weatherappassessment.dataExceptionHandling.DataException
import com.example.weatherappassessment.models.FiveDayWeatherResponse
import com.example.weatherappassessment.models.WeatherItem
import com.example.weatherappassessment.models.WeatherResponse
import com.example.weatherappassessment.reusableComponents.WeatherCard
import com.example.weatherappassessment.ui.theme.WeatherAppAssessmentTheme
import com.example.weatherappassessment.ui.theme.customsPoppinsFont
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.suspendCancellableCoroutine
import java.nio.file.WatchEvent
import kotlin.coroutines.resume


@RequiresApi(Build.VERSION_CODES.O)
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
                    HeadingDisplay()
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HeadingDisplay( modifier: Modifier = Modifier) {

    




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

    val fiveDayForecast = produceState<DataException<FiveDayWeatherResponse, Boolean, Exception>>(
        initialValue = DataException(loading = true)
    ) {
        value = mainViewModel.getFiveDayWeatherForecast(
            lat = getLocation(context)?.latitude.toString(),
            lon = getLocation(context)?.longitude.toString()
        )
    }.value


    if (weatherData.loading == true) {
        Log.e("", "========================== loading")
    } else if (weatherData.data != null && fiveDayForecast.data !=null) {
       // Log.e("", "========================== data  = ${weatherData.data}")
        Log.e("", "++++++++++++++++++++++++++ data  = ${fiveDayForecast.data!!.list}")
        Log.e("", "++++++++++++++++++++++++++ data  size= ${fiveDayForecast.data!!.list.size}")

        DisplayWeatherData(data = weatherData.data!!, fiveDayForecast.data!!)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayWeatherData(data: WeatherResponse, fiveDayWeatherResponse: FiveDayWeatherResponse, modifier: Modifier = Modifier){


    Box(modifier = Modifier
        .fillMaxHeight()) {


        Image(painter = painterResource(id = setBackgroundImage(data.daily.first().weather.first().id)),
            contentDescription = data.daily.first().weather.first().description,
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 17.dp)

        ) {
            Text(
                text = "5 Day Forecast",
                modifier = Modifier.padding(start = 10.dp),
                style = TextStyle(
                    fontFamily = customsPoppinsFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    letterSpacing = 0.em,
                    color = Color.White
                )
            )

            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp), color = Color.White)

//            Text(
//                text = " Temperature: ${data.daily.first().temp.day}°C",
//                modifier = modifier.padding(vertical = 15.dp)
//            )

            DisplayForecastList(list = fiveDayWeatherResponse.list, modifier = modifier )
        }

    }


}

fun fahrenheitToCelsius(temp : Double) : Double{

    return (temp - 32) * (5/9)

}

fun setBackgroundImage(id: Int) : Int{

    var imageId : Int =0

    when(id){
       800 -> imageId = R.drawable.sunny
        in 801..804 -> imageId = R.drawable.cloudy
        in 500..531 -> imageId = R.drawable.rainy
    }


   return imageId
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayForecastList(list : List<WeatherItem>, modifier: Modifier){


    LazyColumn(
        modifier = Modifier.
        fillMaxSize()
            .padding(horizontal = 15.dp)

    ) {

        items(getDailyForecasts(list)){ weatherItem ->

            WeatherCard(weather = weatherItem, modifier = modifier)
        }
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


fun getDailyForecasts(items: List<WeatherItem>): List<WeatherItem> {
    return items.groupBy { it.dt_txt.substring(0, 10) }
        .map { (_, forecasts) ->
            forecasts.first()
        }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppAssessmentTheme {
        HeadingDisplay()
    }
}