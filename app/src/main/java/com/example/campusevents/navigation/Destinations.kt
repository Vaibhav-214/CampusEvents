package com.example.campusevents.navigation


sealed class Screens(val route: String) {
    object CalenderScreen : Screens("Calendar")
    object AddEventScreen : Screens("AddEvent")
    object EventDetailScreen: Screens("EventDetails/{eventId}") {
        fun createRoute(eventId: String) = "EventDetails/$eventId"
    }
    object EventsListScreen: Screens("EventList")

    object FavouritesScreen: Screens("Favourites")
}
