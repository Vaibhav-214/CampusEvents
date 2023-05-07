package com.example.campusevents.presentation.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.campusevents.R
import com.example.campusevents.list
import com.example.campusevents.navigation.Screens
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.component.day.config.KalendarDayColors
import com.himanshoe.kalendar.model.KalendarEvent
import com.himanshoe.kalendar.model.KalendarType
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDate
import java.time.ZoneId


// just need to pass actual events from firestore in Kalendar composable
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    viewModel: CalendarViewModel,
    navController: NavHostController
) {

    val events by remember {
        viewModel.uiState
    }.collectAsState()

    val list = events.map {
        KalendarEvent(
            date = it.item!!.timestamp?.let { timestamp ->
                javaToKotlin(
                    timestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                )
            } ?: kotlinx.datetime.LocalDate(2023, 5, 5),
            eventDescription = it.item.description ?: "You got null",
            eventName = it.item.title ?: "you got null"
        )
    }

    var currentDay by remember {
        mutableStateOf<List<KalendarEvent>>(emptyList())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Campus Events") },
                actions = {
                    IconButton(onClick = { viewModel.getAllEvents() }) {
                        Icon(imageVector = Icons.Filled.Call, contentDescription = null)
                    }

                    IconButton(onClick = { navController.navigate(Screens.EventsListScreen.route) }) {
                        Icon(imageVector = Icons.Filled.List, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screens.AddEventScreen.route) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Column (modifier = Modifier.padding(it)){
            Kalendar(
                kalendarType = KalendarType.Firey,
                kalendarEvents = list,
                onCurrentDayClick = {kalendarDay, kalendarEvents ->
                    currentDay = list.filter {
                        it.date.month == kalendarDay.localDate.month
                    }
                },
            )

            Events(list = if(currentDay.isNotEmpty()) currentDay else emptyList())

        }
    }
}


@Composable
fun Events(
    list: List<KalendarEvent>
) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ){

        Text(text = "This Month's Events", style = MaterialTheme.typography.headlineLarge)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(list) {
                EventItem(event = it)
            }
        }
    }
}

@Composable
fun EventItem(
    event: KalendarEvent,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            //.height(65.dp)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        color = colorResource(id = R.color.new_purple),
    ) {
        Column {
            Text(
                text = event.eventName,
                modifier = Modifier.padding(10.dp),
                color = Color.White,
                fontSize = 30.sp
            )

            Text(
                text = event.date.toString(),
                modifier = Modifier.padding(10.dp),
                color = Color.White,
                fontSize = 30.sp
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun javaToKotlin(javaDate: LocalDate): kotlinx.datetime.LocalDate {
    val javaLocalDateTime = javaDate.atStartOfDay()
    val kotlinLocalDateTime = javaLocalDateTime.toKotlinLocalDateTime()
    val kotlinLocalDate = kotlinLocalDateTime.date
    return kotlinLocalDate
}