package com.example.weatherappassessment.reusableComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.weatherappassessment.models.Weather
import com.example.weatherappassessment.models.WeatherItem
import com.example.weatherappassessment.ui.theme.customsPoppinsFont
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherCard(
    weather: WeatherItem,
   // onCardClicked: (Weather) -> Unit,
    modifier: Modifier
){

    Row(modifier = Modifier.fillMaxWidth()
        .padding(8.dp)
        .background(Color.White)
        .clip(RoundedCornerShape(12.dp))
        .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = getDayOfWeek(weather.dt_txt),
          fontFamily = customsPoppinsFont,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp)


    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeek(dateTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val localDateTime = LocalDateTime.parse(dateTime, formatter)
    val dayOfWeek: DayOfWeek = localDateTime.dayOfWeek
    return dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
}

