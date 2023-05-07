package com.example.campusevents.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

interface EventsRepository {

    fun getTodaysEvents(): Flow<ResultState<List<FireStoreModelResponse>>>

    fun addEvent(event: FireStoreModelResponse.Event): Flow<ResultState<String>>

    fun deleteEvent(id: String): Flow<ResultState<String>>

    fun updateEvent(event: FireStoreModelResponse): Flow<ResultState<String>>

    fun getEvent(id: String): Flow<ResultState<FireStoreModelResponse.Event>>

    fun addToFavourites(id: String):Flow<ResultState<String>>

    fun getFavourites(): Flow<ResultState<List<FireStoreModelResponse>>>

    fun removeFavourite(id: String): Flow<ResultState<String>>

}