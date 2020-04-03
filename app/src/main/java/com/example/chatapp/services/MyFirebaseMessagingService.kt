package com.example.chatapp.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {


    //this callback only gets triggered when the app is in the foreground
    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            //TODO later we will show a notification when the chat room fragment is not in view
            println("Mohamed FCM Message Received : ${message.data}")
        }
    }
}