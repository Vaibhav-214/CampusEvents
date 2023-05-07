package com.example.campusevents.firebase

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.campusevents.dataStore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventsFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        //Log incoming message
        Log.v("CloudMessage", "From ${message.from}")

        //Log data payload
        if (message.data.isNotEmpty()) {
            Log.v("CloudMessage", "Message data: ${message.data}")
        }

        //check if msg contains a notification payload

        message.data?.let {
            Log.v("CloudMessage", "Message Notification Body ${it["body"]}")
        }

        if (message.notification != null) {
            Log.v("CloudMessage", "Notification ${message.notification}")
            Log.v("CloudMessage", "Notification title: ${message.notification!!.title}")
            Log.v("CloudMessage", "Notification body: ${message.notification!!.body}")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        GlobalScope.launch {
            saveGCMToken(token)
        }
    }

    private suspend fun saveGCMToken(token: String) {
        val gcmTokenKey = stringSetPreferencesKey("gcm_token")
        baseContext.dataStore.edit { pref ->
            pref[gcmTokenKey] = setOf(token)
        }
    }
}