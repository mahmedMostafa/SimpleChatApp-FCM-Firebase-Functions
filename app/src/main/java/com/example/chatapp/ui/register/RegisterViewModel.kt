package com.example.chatapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.models.User
import com.example.chatapp.ui.register.status.*
import com.example.chatapp.utils.Firebase.auth
import com.example.chatapp.utils.Firebase.firestore
import com.example.chatapp.utils.Util

enum class status { SUCCESS, ERROR, LOADING }

class RegisterViewModel : ViewModel() {

    private val _status = MutableLiveData<status>()

    val status: LiveData<status>
        get() = _status

    fun signUp(email: String, password: String, name: String) {
        _status.value = LOADING
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.uid?.let {
                        val user = User(name, email, auth.uid ?: "", "")
                        firestore.collection("users/")
                            .document(it)
                            .set(user)
                            .addOnCompleteListener {
                                _status.value = SUCCESS
                                Util.getToken()
                            }
                    }
                } else {
                    _status.value = ERROR
                }
            }
    }

}
