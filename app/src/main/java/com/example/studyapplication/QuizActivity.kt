package com.example.studyapplication

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyapplication.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizAdapter: QuizAdapter
    private lateinit var quizQuestions: List<QuizQuestion>
    private var score = 0
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize quiz data (This can be dynamic, fetched from a server or database)
        quizQuestions = listOf(
            QuizQuestion("What is 2 + 2?", listOf("3", "4", "5"), "4"),
            QuizQuestion("Is the Earth round?", listOf("True", "False"), "True"),
            QuizQuestion("Which planet is known as the Red Planet?", listOf("Mars", "Earth", "Jupiter"), "Mars")
        )

        // Set up RecyclerView for displaying quiz questions
        binding.quizRecyclerView.layoutManager = LinearLayoutManager(this)
        quizAdapter = QuizAdapter(quizQuestions)
        binding.quizRecyclerView.adapter = quizAdapter

        // Start timer for timed tests (e.g., 10 minutes)
        startTimer(10 * 60 * 1000)  // 10 minutes timer

        // Handle Submit Quiz Button
        binding.submitQuizButton.setOnClickListener {
            score = calculateScore()
            Toast.makeText(this, "Your Score: $score", Toast.LENGTH_SHORT).show()
            // Optionally, save the score to a database or server for tracking
        }
    }

    private fun startTimer(millisInFuture: Long) {
        timer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                binding.timerText.text = "Time left: $seconds seconds"
            }

            override fun onFinish() {
                binding.timerText.text = "Time's up!"
                // Automatically submit the quiz if time runs out
                score = calculateScore()
                Toast.makeText(this@QuizActivity, "Time's up! Your Score: $score", Toast.LENGTH_SHORT).show()
            }
        }
        timer?.start()
    }

    private fun calculateScore(): Int {
        var score = 0
        quizQuestions.forEach { question ->
            if (question.selectedAnswer == question.correctAnswer) {
                score++
            }
        }
        return score
    }
}

data class QuizQuestion(val question: String, val options: List<String>, val correctAnswer: String, var selectedAnswer: String? = null)

class QuizAdapter(private val quizQuestions: List<QuizQuestion>) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: android.widget.TextView = itemView.findViewById(android.R.id.text1)
        val optionsRadioGroup: android.widget.RadioGroup = itemView.findViewById(android.R.id.widget_frame)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): QuizViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val question = quizQuestions[position]
        holder.questionTextView.text = question.question

        // Clear previous selections
        holder.optionsRadioGroup.clearCheck()

        // Add radio buttons for options
        holder.optionsRadioGroup.removeAllViews()
        question.options.forEach { option ->
            val radioButton = android.widget.RadioButton(holder.itemView.context)
            radioButton.text = option
            holder.optionsRadioGroup.addView(radioButton)
        }

        // Set selected answer (when user selects an option)
        holder.optionsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = holder.itemView.findViewById<android.widget.RadioButton>(checkedId)
            question.selectedAnswer = selectedRadioButton.text.toString()
        }
    }

    override fun getItemCount(): Int {
        return quizQuestions.size
    }
}
