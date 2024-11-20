package com.example.studyapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyapplication.databinding.ActivityStudyMaterialsBinding

class StudyMaterialsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudyMaterialsBinding
    private lateinit var studyMaterialAdapter: StudyMaterialAdapter
    private lateinit var studyMaterialsList: MutableList<StudyMaterial>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyMaterialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize list for study materials
        studyMaterialsList = mutableListOf()

        // Set up RecyclerView to display the study materials
        binding.studyMaterialsRecyclerView.layoutManager = LinearLayoutManager(this)
        studyMaterialAdapter = StudyMaterialAdapter(studyMaterialsList)
        binding.studyMaterialsRecyclerView.adapter = studyMaterialAdapter

        // Load some sample study materials (can be replaced with real data from a server)
        loadStudyMaterials()

        // Implement search functionality
        binding.searchButton.setOnClickListener {
            val searchQuery = binding.searchEditText.text.toString().trim()
            filterStudyMaterials(searchQuery)
        }
    }

    private fun loadStudyMaterials() {
        // Sample data (This could be replaced by real data from a database or API)
        studyMaterialsList.add(StudyMaterial("Math - Algebra", "PDF", "algebra.pdf"))
        studyMaterialsList.add(StudyMaterial("Science - Physics", "Video", "physics_intro.mp4"))
        studyMaterialsList.add(StudyMaterial("History - Ancient Rome", "PDF", "ancient_rome.pdf"))
        studyMaterialsList.add(StudyMaterial("Math - Geometry", "Quiz", "geometry_quiz"))
        studyMaterialsAdapter.notifyDataSetChanged()
    }

    private fun filterStudyMaterials(query: String) {
        val filteredList = studyMaterialsList.filter {
            it.title.contains(query, ignoreCase = true)
        }
        studyMaterialAdapter.updateList(filteredList)
    }
}

data class StudyMaterial(val title: String, val contentType: String, val fileName: String)

class StudyMaterialAdapter(private var studyMaterialList: List<StudyMaterial>) :
    RecyclerView.Adapter<StudyMaterialAdapter.StudyMaterialViewHolder>() {

    inner class StudyMaterialViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: android.widget.TextView = itemView.findViewById(android.R.id.text1)
        val contentTypeTextView: android.widget.TextView = itemView.findViewById(android.R.id.text2)
        val downloadButton: android.widget.Button = itemView.findViewById(android.R.id.button1)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): StudyMaterialViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return StudyMaterialViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudyMaterialViewHolder, position: Int) {
        val studyMaterial = studyMaterialList[position]
        holder.titleTextView.text = studyMaterial.title
        holder.contentTypeTextView.text = studyMaterial.contentType
        holder.downloadButton.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Downloading: ${studyMaterial.title}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return studyMaterialList.size
    }

    fun updateList(newList: List<StudyMaterial>) {
        studyMaterialList = newList
        notifyDataSetChanged()
    }
}
