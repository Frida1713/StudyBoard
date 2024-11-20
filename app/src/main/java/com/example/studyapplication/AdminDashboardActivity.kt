package com.example.studyapplication

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        // Feature links (LinearLayout and TextView)
        val lnAdminProfile: LinearLayout = findViewById(R.id.lnAdminProfile)
        val tvAdminProfile: TextView = findViewById(R.id.tvAdminProfile)

        val lnUserManagement: LinearLayout = findViewById(R.id.lnUserManagement)
        val tvUserManagement: TextView = findViewById(R.id.tvUserManagement)

        val lnContentManagement: LinearLayout = findViewById(R.id.lnContentManagement)
        val tvContentManagement: TextView = findViewById(R.id.tvContentManagement)

        val lnQuizTestManagement: LinearLayout = findViewById(R.id.lnQuizTestManagement)
        val tvQuizTestManagement: TextView = findViewById(R.id.tvQuizTestManagement)

        val lnProgressMonitoring: LinearLayout = findViewById(R.id.lnProgressMonitoring)
        val tvProgressMonitoring: TextView = findViewById(R.id.tvProgressMonitoring)

        val lnPushNotificationManagement: LinearLayout = findViewById(R.id.lnPushNotificationManagement)
        val tvPushNotificationManagement: TextView = findViewById(R.id.tvPushNotificationManagement)

        val lnContentApproval: LinearLayout = findViewById(R.id.lnContentApproval)
        val tvContentApproval: TextView = findViewById(R.id.tvContentApproval)

        val lnAnalyticsDashboard: LinearLayout = findViewById(R.id.lnAnalyticsDashboard)
        val tvAnalyticsDashboard: TextView = findViewById(R.id.tvAnalyticsDashboard)

        // Set click listeners for both LinearLayouts and TextViews

        val clickListener = { featureName: String ->
            navigateToFeature(featureName)
        }

        // Set click listeners for the LinearLayouts (already clickable)
        lnAdminProfile.setOnClickListener { navigateToFeature("Admin Profile Management") }
        lnUserManagement.setOnClickListener { navigateToFeature("User Management") }
        lnContentManagement.setOnClickListener { navigateToFeature("Content Management") }
        lnQuizTestManagement.setOnClickListener { navigateToFeature("Quiz/Test Management") }
        lnProgressMonitoring.setOnClickListener { navigateToFeature("Progress Monitoring") }
        lnPushNotificationManagement.setOnClickListener { navigateToFeature("Push Notification Management") }
        lnContentApproval.setOnClickListener { navigateToFeature("Content Approval") }
        lnAnalyticsDashboard.setOnClickListener { navigateToFeature("Analytics Dashboard") }

        // Set click listeners for the TextViews
        tvAdminProfile.setOnClickListener { clickListener("Admin Profile Management") }
        tvUserManagement.setOnClickListener { clickListener("User Management") }
        tvContentManagement.setOnClickListener { clickListener("Content Management") }
        tvQuizTestManagement.setOnClickListener { clickListener("Quiz/Test Management") }
        tvProgressMonitoring.setOnClickListener { clickListener("Progress Monitoring") }
        tvPushNotificationManagement.setOnClickListener { clickListener("Push Notification Management") }
        tvContentApproval.setOnClickListener { clickListener("Content Approval") }
        tvAnalyticsDashboard.setOnClickListener { clickListener("Analytics Dashboard") }
    }

    private fun navigateToFeature(featureName: String) {
        Toast.makeText(this, "Navigating to $featureName", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, FeatureDetailActivity::class.java)
        intent.putExtra("FEATURE_NAME", featureName)
        startActivity(intent)
    }
}
