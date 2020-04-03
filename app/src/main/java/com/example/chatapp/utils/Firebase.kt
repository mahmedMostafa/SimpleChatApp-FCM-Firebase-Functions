package com.example.chatapp.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object Firebase {

    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val chatRoomUserRef: DocumentReference by lazy {
        firestore.collection("chat_room/")
            .document("first_chat")
            .collection("user_list/")
            .document(auth.uid!!)
    }

    val userRef: DocumentReference by lazy {
        firestore.collection("users/")
            .document(auth.uid!!)
    }

    val messagesRef: CollectionReference by lazy {
        firestore.collection("chat_rooms/")
            .document("first_chat")
            .collection("messages/")
    }

    val chatRoomUserListRef: CollectionReference by lazy {
        firestore.collection("chat_rooms/")
            .document("first_chat")
            .collection("user_list")
    }

}