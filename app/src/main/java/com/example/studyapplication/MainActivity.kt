package com.example.studyapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var startedButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the button
        startedButton = findViewById(R.id.startedButton)

        // Set the OnClickListener to navigate to RegisterActivity
        startedButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Initialize Firebase
        FirebaseMessaging.getInstance().subscribeToTopic("general")
            .addOnCompleteListener { task ->
                var msg = "Subscribed to notifications"
                if (!task.isSuccessful) {
                    msg = "Subscription failed"
                }
                Log.d(TAG, msg)
            }
    }
}
