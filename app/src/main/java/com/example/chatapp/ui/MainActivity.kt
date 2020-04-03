package com.example.chatapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //this is the intent coming from the FCM notification
        val extras = intent.extras
        extras?.let {
            if (it.containsKey("test")) {
                //Do something custom here if you want
                println("FCM INTENT RECEIVED!!!!!!!")
                Toast.makeText(this,"This is coming from FCM",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
