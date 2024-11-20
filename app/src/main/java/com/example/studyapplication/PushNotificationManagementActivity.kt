package com.example.studyapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapplication.databinding.ActivityPushNotificationManagementBinding


class PushNotificationManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPushNotificationManagementBinding
    private lateinit var sentNotificationsAdapter: SentNotificationsAdapter
    private lateinit var sentNotificationsList: MutableList<NotificationHistory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushNotificationManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Toolbar
        setSupportActionBar(binding.toolbar)

        // Initialize sent notifications list
        sentNotificationsList = mutableListOf()

        // Set up RecyclerView for sent notifications history
        binding.sentNotificationsRecyclerView.layoutManager = LinearLayoutManager(this)
        sentNotificationsAdapter = SentNotificationsAdapter(sentNotificationsList)
        binding.sentNotificationsRecyclerView.adapter = sentNotificationsAdapter

        // Handle Send Notification Button Click
        binding.sendNotificationButton.setOnClickListener {
            val title = binding.notificationTitleInput.text.toString()
            val message = binding.notificationMessageInput.text.toString()
            if (title.isNotEmpty() && message.isNotEmpty()) {
                sendNotification(title, message)
            } else {
                Toast.makeText(this, "Please fill in both title and message", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Schedule Notification Button Click
        binding.scheduleNotificationButton.setOnClickListener {
            val title = binding.notificationTitleInput.text.toString()
            val message = binding.notificationMessageInput.text.toString()
            // Implement logic for scheduling notification (could be a date picker dialog)
            if (title.isNotEmpty() && message.isNotEmpty()) {
                scheduleNotification(title, message)
            } else {
                Toast.makeText(this, "Please fill in both title and message", Toast.LENGTH_SHORT).show()
            }
        }

        // Load some dummy notification history (can be replaced with real data)
        loadSentNotificationsHistory()
    }

    private fun sendNotification(title: String, message: String) {
        // Logic to send notification (In real app, send it through Firebase or other service)
        Toast.makeText(this, "Sending notification: $title", Toast.LENGTH_SHORT).show()

        // After sending, add to the history
        sentNotificationsList.add(NotificationHistory(title, message, "Sent", System.currentTimeMillis()))
        sentNotificationsAdapter.notifyDataSetChanged()
    }

    private fun scheduleNotification(title: String, message: String) {
        // Logic to schedule notification (this can use AlarmManager or Firebase Cloud Messaging for scheduling)
        Toast.makeText(this, "Scheduling notification: $title", Toast.LENGTH_SHORT).show()
    }

    private fun loadSentNotificationsHistory() {
        // Load some dummy data (Replace with real data from Firebase or backend)
        sentNotificationsList.add(NotificationHistory("Reminder: Quiz Tomorrow", "Don't forget to take the quiz tomorrow.", "Sent", System.currentTimeMillis()))
        sentNotificationsList.add(NotificationHistory("New Study Material", "New chapter study material uploaded.", "Sent", System.currentTimeMillis()))
        sentNotificationsAdapter.notifyDataSetChanged()
    }
}

// Data class to represent sent notification history
data class NotificationHistory(
    val title: String,
    val message: String,
    val status: String,  // Sent/Failed/Delivered
    val timestamp: Long  // Time when notification was sent
)

// RecyclerView Adapter to display sent notifications history
class SentNotificationsAdapter(private val notificationList: MutableList<NotificationHistory>) : RecyclerView.Adapter<SentNotificationsAdapter.NotificationHistoryViewHolder>() {

    inner class NotificationHistoryViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: android.widget.TextView = itemView.findViewById(R.id.titleTextView)
        val messageTextView: android.widget.TextView = itemView.findViewById(R.id.messageTextView)
        val statusTextView: android.widget.TextView = itemView.findViewById(R.id.statusTextView)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): NotificationHistoryViewHolder {
        val view = android.view.LayoutInflater.from(parent.context).inflate(R.layout.item_notification_history, parent, false)
        return NotificationHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationHistoryViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.titleTextView.text = notification.title
        holder.messageTextView.text = notification.message
        holder.statusTextView.text = notification.status
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    fun updateList(newList: List<NotificationHistory>) {
        notificationList.clear()
        notificationList.addAll(newList)
        notifyDataSetChanged()
    }
}


