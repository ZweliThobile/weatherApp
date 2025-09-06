package com.example.weatherappassessment.reusableComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.weatherappassessment.R
import com.example.weatherappassessment.models.Weather
import com.example.weatherappassessment.models.WeatherItem
import com.example.weatherappassessment.ui.theme.customsPoppinsFont
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek
import java.util.Locale
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherCard(
    weather: WeatherItem,
   // onCardClicked: (Weather) -> Unit,
    modifier: Modifier = Modifier
){

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .height(125.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(Color.White)
        .padding(horizontal = 12.dp)) {

        Text(text = getDayOfWeek(weather.dt_txt),
            modifier = Modifier.align(Alignment.TopStart),
          fontFamily = customsPoppinsFont,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp)
        
        Image(painter = painterResource(id = R.drawable.sun_light_icon), contentDescription ="weather icon",
            modifier = Modifier.size(70.dp).align(Alignment.CenterStart).padding(top = 15.dp))


        Text(
            text = "${weather.main.temp.roundToInt()}°",
            fontFamily = customsPoppinsFont,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.BottomEnd)
        )


    }

}



@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeek(dateTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val localDateTime = LocalDateTime.parse(dateTime, formatter)
    val dayOfWeek: DayOfWeek = localDateTime.dayOfWeek
    return dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
}

