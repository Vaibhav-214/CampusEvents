package com.example.campusevents

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.campusevents.navigation.AppNavHost
import com.example.campusevents.presentation.add_event.AddEvent
import com.example.campusevents.presentation.calendar.Calendar
import com.example.campusevents.ui.theme.CampusEventsTheme
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.component.header.config.KalendarHeaderConfig
import com.himanshoe.kalendar.component.text.config.KalendarTextConfig
import com.himanshoe.kalendar.model.KalendarEvent
import com.himanshoe.kalendar.model.KalendarType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.LocalDate

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusEventsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }
            }
        }
    }
}

var list = listOf<KalendarEvent>(
    KalendarEvent(
        date = LocalDate(2023,5,2),
        eventName = "Making calender view",
        eventDescription = "Using Kalendar library"
    ),
    KalendarEvent(
        date = LocalDate(2023,5,3),
        eventName = "Making other view",
        eventDescription = "wjojso"
    )
)

