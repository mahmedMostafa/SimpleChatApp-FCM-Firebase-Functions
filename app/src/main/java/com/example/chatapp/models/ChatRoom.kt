package com.example.chatapp.models

data class ChatRoom(
    var title: String = "",
    var roomId: String = "",
    var userIds: MutableList<String> = mutableListOf()
)