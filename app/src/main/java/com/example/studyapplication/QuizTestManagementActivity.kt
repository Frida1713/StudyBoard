package com.example.studyapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyapplication.databinding.ActivityQuizTestManagementBinding

class QuizTestManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizTestManagementBinding
    private lateinit var quizTestAdapter: QuizTestAdapter
    private lateinit var quizTestList: MutableList<QuizTestItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizTestManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizTestList = mutableListOf()

        // Set up Toolbar
        setSupportActionBar(binding.toolbar)

        // Set up RecyclerView
        binding.quizTestRecyclerView.layoutManager = LinearLayoutManager(this)
        quizTestAdapter = QuizTestAdapter(quizTestList)
        binding.quizTestRecyclerView.adapter = quizTestAdapter

        // Handle Create Quiz/Test Button Click
        binding.createQuizButton.setOnClickListener {
            // Create a new Quiz/Test (In a real app, show dialog to fill details)
            val newQuiz = QuizTestItem("New Quiz", "Math", "2024-12-10", "60 minutes")
            quizTestList.add(newQuiz)
            quizTestAdapter.notifyDataSetChanged()
            Toast.makeText(this, "New Quiz/Test Created", Toast.LENGTH_SHORT).show()
        }

        // Handle Upload Assignment Button Click
        binding.uploadAssignmentButton.setOnClickListener {
            // Trigger assignment upload flow (In real app, trigger file picker or upload form)
            Toast.makeText(this, "Upload Assignment", Toast.LENGTH_SHORT).show()
        }

        // Handle View Results Button Click
        binding.viewResultsButton.setOnClickListener {
            // Show results of quizzes/tests (In a real app, navigate to a results screen)
            Toast.makeText(this, "Viewing Results", Toast.LENGTH_SHORT).show()
        }

        // Dummy data for quizzes and tests
        loadQuizzesAndTests()
    }

    private fun loadQuizzesAndTests() {
        // Sample data (In real implementation, this would come from a database or API)
        quizTestList.add(QuizTestItem("Math Quiz", "Math", "2024-11-20", "30 minutes"))
        quizTestList.add(QuizTestItem("Science Test", "Science", "2024-11-25", "45 minutes"))
        quizTestAdapter.notifyDataSetChanged()
    }
}

// Data class to represent Quiz/Test Item
data class QuizTestItem(val title: String, val category: String, val date: String, val timeLimit: String)

// RecyclerView Adapter for Quiz/Test Management
class QuizTestAdapter(private val itemList: List<QuizTestItem>) : RecyclerView.Adapter<QuizTestAdapter.QuizTestViewHolder>() {

    inner class QuizTestViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: android.widget.TextView = itemView.findViewById(android.R.id.text1)
        val categoryTextView: android.widget.TextView = itemView.findViewById(android.R.id.text2)
        val dateTextView: android.widget.TextView = itemView.findViewById(android.R.id.text3)
        val timeLimitTextView: android.widget.TextView = itemView.findViewById(android.R.id.text4)
        val editButton: android.widget.Button = itemView.findViewById(android.R.id.button1)
        val deleteButton: android.widget.Button = itemView.findViewById(android.R.id.button2)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): QuizTestViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_4, parent, false)
        return QuizTestViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizTestViewHolder, position: Int) {
        val item = itemList[position]
        holder.titleTextView.text = item.title
        holder.categoryTextView.text = item.category
        holder.dateTextView.text = "Date: ${item.date}"
        holder.timeLimitTextView.text = "Time: ${item.timeLimit}"

        // Handle edit and delete buttons
        holder.editButton.setOnClickListener {
            // Show dialog to edit quiz/test details (In real app, you can navigate to an edit screen)
            Toast.makeText(holder.itemView.context, "Editing ${item.title}", Toast.LENGTH_SHORT).show()
        }

        holder.deleteButton.setOnClickListener {
            // Delete quiz/test (In real app, show confirmation dialog)
            Toast.makeText(holder.itemView.context, "Deleted ${item.title}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = itemList.size
}
