package com.example.chatapp.utils

import android.app.Activity
import android.provider.SyncStateContract.Helpers.update
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.chatapp.utils.Firebase.auth
import com.google.firebase.iid.FirebaseInstanceId

object Util {

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result?.token
                    token?.let {
                            Firebase.userRef.update(mapOf("token" to it))
                            Firebase.chatRoomUserRef.update(mapOf("token" to it))
                    }
                }
            }
    }
}