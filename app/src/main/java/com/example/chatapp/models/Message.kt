package com.example.chatapp.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    var messageId: String = "",
    var text: String = "",
    var user: User? = null,
    @ServerTimestamp
    var timestamp: Date? = null
) {
    //constructor(messageId: String, text: String, user: User?) : this("", "", User(), null)
}