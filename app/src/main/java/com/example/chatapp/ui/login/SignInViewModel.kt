package com.example.chatapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.models.User
import com.example.chatapp.ui.login.status.*
import com.example.chatapp.utils.Firebase
import com.example.chatapp.utils.Firebase.auth
import com.example.chatapp.utils.Util

enum class status { SUCCESS, ERROR, LOADING }

class SignInViewModel : ViewModel() {

    private val _status = MutableLiveData<status>()

    val status: LiveData<status>
        get() = _status

    fun isLoggedInAlready() : Boolean{
        return auth.currentUser != null
    }

    fun loginUser(email: String, password: String) {
        _status.value = LOADING
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _status.value = SUCCESS

                Firebase.userRef.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        println("Mohamed man")
                        val user = it.result?.toObject(User::class.java)
                        println(user.toString())
                        Util.getToken()
                    }
                }
            } else {
                _status.value = ERROR
            }
        }
    }

}
