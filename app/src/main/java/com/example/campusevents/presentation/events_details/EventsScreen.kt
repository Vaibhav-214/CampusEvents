package com.example.campusevents.presentation.events_details

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsDetail(
    eventId: String,
    viewModel: EventDetailsViewModel,
    navController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getEvent(eventId)
    }

    val uiState by remember {
        viewModel.uiState
    }.collectAsState()

    Log.d("UI", "uiState $uiState and title: ${uiState?.title}")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Event Details") },
                actions = {

                    IconButton(onClick = {
                        viewModel.deleteEvent(eventId)
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }
                }
            )
        },
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(10.dp)
        ) {

            TextComponent(
                text = uiState?.title ?: "",
                style = MaterialTheme.typography.headlineLarge,
                height = 50,
                alignment = Alignment.CenterStart
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextComponent(
                text = uiState?.description ?: "",
                style = MaterialTheme.typography.headlineMedium,
                height = 200,
                alignment = Alignment.TopStart
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextComponent(
                text = uiState?.timestamp?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())
                    ?.toLocalDate()?.toString()
                    ?: "null",
                style = MaterialTheme.typography.headlineMedium,
                height = 60,
                alignment = Alignment.CenterStart
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextComponent(
                text = uiState?.timestamp?.let { it ->
                    it.toDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalTime().toString()
                } ?: "null",
                style = MaterialTheme.typography.headlineMedium,
                height = 60,
                alignment = Alignment.CenterStart
            )

            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}