package com.example.studyapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapplication.databinding.ActivityAnalyticsDashboardBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class AnalyticsDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalyticsDashboardBinding
    private lateinit var reportAdapter: DetailedReportAdapter
    private lateinit var reportList: MutableList<ReportItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyticsDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Toolbar
        setSupportActionBar(binding.toolbar)

        // Initialize list for reports
        reportList = mutableListOf()

        // Set up RecyclerView for detailed report view
        binding.detailedReportsRecyclerView.layoutManager = LinearLayoutManager(this)
        reportAdapter = DetailedReportAdapter(reportList)
        binding.detailedReportsRecyclerView.adapter = reportAdapter

        // Display usage stats
        displayUsageStats()

        // Set up the Engagement Chart
        setUpEngagementChart()

        // Set up Generate Custom Report Button
        binding.generateCustomReportButton.setOnClickListener {
            // Implement logic for generating custom report based on filters
            Toast.makeText(this, "Generating custom report...", Toast.LENGTH_SHORT).show()
            generateCustomReport()
        }

        // Load sample reports for the RecyclerView
        loadSampleReports()
    }

    private fun displayUsageStats() {
        // Display the usage statistics (replace these with real data)
        binding.activeUsersText.text = "Active Users: 1200"
        binding.appDownloadsText.text = "App Downloads: 5000"
    }

    private fun setUpEngagementChart() {
        // Create sample engagement data for the Line Chart (replace with real data)
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 50f))  // Day 1: 50 users engaged
        entries.add(Entry(1f, 75f))  // Day 2: 75 users engaged
        entries.add(Entry(2f, 100f)) // Day 3: 100 users engaged
        entries.add(Entry(3f, 90f))  // Day 4: 90 users engaged
        entries.add(Entry(4f, 120f)) // Day 5: 120 users engaged

        val dataSet = LineDataSet(entries, "User Engagement")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList() // Set chart colors
        val lineData = LineData(dataSet)

        // Set the chart data
        binding.engagementChart.data = lineData
        binding.engagementChart.invalidate() // Refresh chart
    }

    private fun generateCustomReport() {
        // Logic to generate a custom report based on filters (date range, user type, etc.)
        // For now, we'll simulate with dummy data.
        Toast.makeText(this, "Custom Report Generated", Toast.LENGTH_SHORT).show()
    }

    private fun loadSampleReports() {
        // Sample reports to display in the RecyclerView
        reportList.add(ReportItem("Math Quiz Performance", "85% average score"))
        reportList.add(ReportItem("History Course Engagement", "70% of students active"))
        reportAdapter.notifyDataSetChanged()
    }

}

// Data class for report items to be displayed in RecyclerView
data class ReportItem(val reportName: String, val details: String)

// Adapter for RecyclerView displaying detailed reports
class DetailedReportAdapter(private val reportList: MutableList<ReportItem>) :
    RecyclerView.Adapter<DetailedReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val reportNameTextView: android.widget.TextView = itemView.findViewById(android.R.id.text1)
        val detailsTextView: android.widget.TextView = itemView.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ReportViewHolder {
        val view = android.view.LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = reportList[position]
        holder.reportNameTextView.text = report.reportName
        holder.detailsTextView.text = report.details
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    fun addReport(report: ReportItem) {
        reportList.add(report)
        notifyItemInserted(reportList.size - 1)  // Notify adapter of the new item
    }

    fun updateReports(newReports: List<ReportItem>) {
        reportList.clear()
        reportList.addAll(newReports)
        notifyDataSetChanged()  // Refresh RecyclerView
    }
}
