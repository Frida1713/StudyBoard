package com.example.studyapplication

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.studyapplication.databinding.ActivityNotificationManagementBinding
import java.util.*

class NotificationManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)

        // Example of setting a reminder for a study session
        binding.setReminderButton.setOnClickListener {
            val reminderTime = Calendar.getInstance()
            reminderTime.set(Calendar.HOUR_OF_DAY, 18) // Set reminder for 6 PM
            reminderTime.set(Calendar.MINUTE, 0)

            setReminderNotification(reminderTime.timeInMillis)
            Toast.makeText(this, "Reminder set for 6 PM", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setReminderNotification(timeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }
}


