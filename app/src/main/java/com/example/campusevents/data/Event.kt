package com.example.campusevents.data

import com.google.firebase.Timestamp


data class FireStoreModelResponse(
    val item:Event?,
    val id:String? = ""
){
    data class Event (
        val title: String?,
        val description: String?,
        val timestamp: Timestamp?
    )
}