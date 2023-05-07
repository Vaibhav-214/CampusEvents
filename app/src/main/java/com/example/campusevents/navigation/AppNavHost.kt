package com.example.campusevents.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.campusevents.presentation.add_event.AddEvent
import com.example.campusevents.presentation.calendar.Calendar
import com.example.campusevents.presentation.event_list.EventsListScreen
import com.example.campusevents.presentation.events_details.EventsDetail
import com.example.campusevents.presentation.favourite.FavouritesScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost() {
    val navController : NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.CalenderScreen.route ) {

        composable(Screens.CalenderScreen.route) {
            Calendar(viewModel = hiltViewModel(), navController = navController)
        }

        composable(Screens.AddEventScreen.route) {
            AddEvent(viewModel = hiltViewModel())
        }
        
        composable(Screens.EventDetailScreen.route) { navBackStackEntry ->
            val eventId = navBackStackEntry.arguments?.getString("eventId")
            if (eventId == null) {
                Toast.makeText(LocalContext.current, "eventId is required for navigation", Toast.LENGTH_SHORT)
                    .show()
            } else {
                EventsDetail(eventId = eventId, viewModel = hiltViewModel(), navController = navController)
            }
        }

        composable(Screens.EventsListScreen.route) {
            EventsListScreen(navController = navController, viewModel = hiltViewModel())
        }

        composable(Screens.FavouritesScreen.route) {
            FavouritesScreen(viewModel = hiltViewModel(), navController = navController)
        }

    }
}