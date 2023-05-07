package com.example.campusevents.data

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class EventsRepositoryImpl @Inject constructor(
        private val db: FirebaseFirestore
    ): EventsRepository {
    override fun getTodaysEvents(): Flow<ResultState<List<FireStoreModelResponse>>> = callbackFlow {
        trySend(ResultState.Loading)

        db.collection("events")
            .get()
            .addOnSuccessListener {
                val events = it.map { data ->
                    FireStoreModelResponse(
                        item = FireStoreModelResponse.Event(
                            title = data["title"] as String?,
                            description = data["description"] as String?,
                            timestamp = data["timestamp"] as Timestamp?
                            ),
                        id = data.id
                    )
                }
                trySend(ResultState.Success(events))
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

        awaitClose {
            close()
        }
    }

    override fun addEvent(event: FireStoreModelResponse.Event): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        db.collection("events")
            .add(event)
            .addOnSuccessListener {
                trySend(ResultState.Success(it.toString()))
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun deleteEvent(id: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        db.collection("events")
            .document(id)
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful) trySend(ResultState.Success("Deleted Successfully"))
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

        awaitClose {
            close()
        }
    }

    override fun updateEvent(event: FireStoreModelResponse): Flow<ResultState<String>> = callbackFlow {

//        trySend(ResultState.Loading)
//
//        val map = HashMap<Any,Any>()
//
//        map["title"] = event.item?.title!!
//        map["description"]

    }

    override fun getEvent(id: String): Flow<ResultState<FireStoreModelResponse.Event>> = callbackFlow {

        trySend(ResultState.Loading)

        db.collection("events")
            .document(id)
            .get()
            .addOnSuccessListener {data->

                Log.d("REPO", "data: $data")

                val event = FireStoreModelResponse.Event(
                    title = data["title"] as String?,
                    description = data["description"] as String?,
                    timestamp = data["timestamp"] as Timestamp?
                )

                Log.d("REPO", "event: $event")
                trySend(ResultState.Success(event))
            }
            .addOnFailureListener {
               trySend( ResultState.Failure(it))
            }

        awaitClose {
            close()
        }
    }

    override fun addToFavourites(id: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        val eventref = db.collection("events").document(id)
        db.collection("favourite")
            .document()
            .set(
                mapOf(
                    "event" to eventref
                )
            ).addOnSuccessListener {
                trySend(ResultState.Success("Successful"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

        awaitClose {
            close()
        }
    }

override fun getFavourites(): Flow<ResultState<List<FireStoreModelResponse>>> = callbackFlow {
    trySend(ResultState.Loading)

    db.collection("favourite")
        .get()
        .addOnSuccessListener { list ->
            val favouritesList = mutableListOf<FireStoreModelResponse>()
            val tasks = mutableListOf<Task<DocumentSnapshot>>()

            for (documentReference in list) {
                val eventRef = documentReference.get("event") as DocumentReference?
                if (eventRef != null) {
                    val task = eventRef.get()
                    tasks.add(task)
                    task.addOnSuccessListener { data ->
                        val fireStoreModelResponse = FireStoreModelResponse(
                            id = documentReference.id,
                            item = FireStoreModelResponse.Event(
                                title = data["title"] as String?,
                                description = data["description"] as String?,
                                timestamp = data["timestamp"] as Timestamp?
                            )
                        )
                        favouritesList.add(fireStoreModelResponse)
                    }
                }
            }

            Tasks.whenAllSuccess<DocumentSnapshot>(tasks)
                .addOnSuccessListener {
                    trySend(ResultState.Success(favouritesList))
                }
                .addOnFailureListener { exception ->
                    trySend(ResultState.Failure(exception))
                }
        }
        .addOnFailureListener { exception ->
            trySend(ResultState.Failure(exception))
        }

    awaitClose {
        close()
    }
}



    override fun removeFavourite(id: String): Flow<ResultState<String>> = callbackFlow {
       trySend(ResultState.Loading)

        db.collection("favourite")
            .document(id)
            .delete()
            .addOnSuccessListener {
                trySend(ResultState.Success("Deleted Successfully"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

        awaitClose {
            close()
        }

    }

}