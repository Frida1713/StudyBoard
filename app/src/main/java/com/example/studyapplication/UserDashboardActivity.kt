package com.example.studyapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        // Study Materials Button Click Listener
        val btnStudyMaterials = findViewById<Button>(R.id.btnStudyMaterials)
        btnStudyMaterials.setOnClickListener {
            // Navigate to Study Materials activity
            val intent = Intent(this, StudyMaterialsActivity::class.java)
            startActivity(intent)
        }

        // Quizzes/Tests Button Click Listener
        val btnQuizzesTests = findViewById<Button>(R.id.btnQuizzesTests)
        btnQuizzesTests.setOnClickListener {
            // Navigate to Quizzes/Tests activity
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }

        // Notes and Bookmarks Button Click Listener
        val btnNotesBookmarks = findViewById<Button>(R.id.btnNotesBookmarks)
        btnNotesBookmarks.setOnClickListener {
            // Navigate to Notes and Bookmarks activity
            val intent = Intent(this, NotesAndBookmarksActivity::class.java)
            startActivity(intent)
        }

        // Push Notifications Button Click Listener
        val btnPushNotifications = findViewById<Button>(R.id.btnPushNotifications)
        btnPushNotifications.setOnClickListener {
            // Navigate to Push Notifications activity
            val intent = Intent(this, NotificationManagementActivity::class.java)
            startActivity(intent)
        }

        // Forum/Discussion Area Button Click Listener
        val btnForum = findViewById<Button>(R.id.btnForum)
        btnForum.setOnClickListener {
            // Navigate to Forum/Discussion Area activity
            val intent = Intent(this, ForumActivity::class.java)
            startActivity(intent)
        }
    }
}

