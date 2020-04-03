package com.example.chatapp.ui.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.models.Message
import com.example.chatapp.models.User
import com.example.chatapp.ui.chatroom.chatStatus.*
import com.example.chatapp.utils.Firebase
import com.example.chatapp.utils.Firebase.auth
import com.example.chatapp.utils.Firebase.messagesRef
import com.example.chatapp.utils.Firebase.chatRoomUserListRef
import com.google.firebase.firestore.Query

enum class chatStatus { ERROR, SUCCESS, LOADING }

class ChatRoomViewModel : ViewModel() {

    private val _status = MutableLiveData<chatStatus>()
    private val _messages = MutableLiveData<List<Message>>()

    val messages: LiveData<List<Message>>
        get() = _messages
    val status: LiveData<chatStatus>
        get() = _status

    init {
        getMessages()
    }

    fun sendMessage(text: String) {
        Firebase.userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.toObject(User::class.java)
                val message = Message("", text, user)
                messagesRef.document()
                    .set(message)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            println("Message has been sent successfully")
                        }
                    }
            }
        }

    }

    fun addUserToChatRoom() {
        Firebase.userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.toObject(User::class.java)
                user?.let {
                    chatRoomUserListRef
                        .document(auth.uid!!)
                        .set(it)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                println("User added to the chat room")
                            }
                        }
                }
            }
        }
    }

    private fun getMessages() {
        _status.value = LOADING
        messagesRef
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    _status.value = ERROR
                    println("Error retrieving messages ${e.message}")
                } else {
                    snapshot?.let {
                        // println("not null and i'm in CALLED")
                        val list = mutableListOf<Message>()
                        for (item in it.documents) {
                            val message = item.toObject(Message::class.java)
                            //println(message.toString())
                            list.add(message!!)
                        }
                        _status.value = SUCCESS
                        _messages.value = list
                    }
                }
            }
    }
}
