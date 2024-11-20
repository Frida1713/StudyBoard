package com.example.studyapplication

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.studyapplication.databinding.ActivityStudentManagementBinding

class StudentManagementActivity : AppCompatActivity() {

    private lateinit var studentList: MutableList<Student>
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStudentManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentList = mutableListOf()
        recyclerView = binding.recyclerView

        // Setting up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        studentAdapter = StudentAdapter(studentList)
        recyclerView.adapter = studentAdapter

        // Toolbar setup
        setSupportActionBar(binding.toolbar)

        // Search functionality
        val searchView: SearchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Search functionality here
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                studentAdapter.filter.filter(newText)
                return true
            }
        })

        // Add student button
        binding.addStudentButton.setOnClickListener {
            // Open Add Student Activity or Dialog
            Toast.makeText(this, "Add Student Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    // Method to delete student
    fun deleteStudent(student: Student) {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Yes") { dialog, _ ->
                studentList.remove(student)
                studentAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("No", null)
            .show()
    }
}

// Sample Data Model for Student
data class Student(val name: String, val email: String, val status: String)

// Adapter for RecyclerView
class StudentAdapter(private val students: List<Student>) : Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: android.view.View) : ViewHolder(itemView) {
        val nameTextView: android.widget.TextView = itemView.findViewById(android.R.id.text1)
        val emailTextView: android.widget.TextView = itemView.findViewById(android.R.id.text2)
        val statusTextView: android.widget.TextView = itemView.findViewById(android.R.id.text3)
        val editButton: android.widget.ImageButton = itemView.findViewById(android.R.id.button1)
        val deleteButton: android.widget.ImageButton = itemView.findViewById(android.R.id.button2)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): StudentViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.nameTextView.text = student.name
        holder.emailTextView.text = student.email
        holder.statusTextView.text = student.status

        // Edit button action
        holder.editButton.setOnClickListener {
            // Open Edit Student Activity or Dialog
        }

        // Delete button action
        holder.deleteButton.setOnClickListener {
            // Call delete method
            (holder.itemView.context as StudentManagementActivity).deleteStudent(student)
        }
    }

    override fun getItemCount(): Int = students.size
}
