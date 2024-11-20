package com.example.studyapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapplication.databinding.ActivityContentManagementBinding

class ContentManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentManagementBinding
    private lateinit var contentAdapter: ContentAdapter
    private lateinit var contentList: MutableList<ContentItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contentList = mutableListOf()

        // Set up Toolbar
        setSupportActionBar(binding.toolbar)

        // Set up the Spinner for Categories (e.g., Math, Science)
        val categories = arrayOf("Math", "Science", "History", "Literature")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        binding.categorySpinner.adapter = spinnerAdapter

        // Set up RecyclerView
        binding.contentRecyclerView.layoutManager = LinearLayoutManager(this)
        contentAdapter = ContentAdapter(contentList)
        binding.contentRecyclerView.adapter = contentAdapter

        // Handle upload content button click
        binding.uploadContentButton.setOnClickListener {
            // This is just an example, open an intent to upload files (you can use an Intent to choose a file)
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*" // You can filter for specific file types (e.g., application/pdf)
            startActivityForResult(intent, UPLOAD_CONTENT_REQUEST_CODE)
        }

        // Dummy data (in real implementation, this will be fetched from a database or API)
        loadContentList()
    }

    // Handling file upload request
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPLOAD_CONTENT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the uploaded file URI from the intent data
            val contentUri = data?.data
            // Create a new ContentItem object and add it to the list
            val newContent = ContentItem("New Content", contentUri.toString(), "Math")
            contentList.add(newContent)
            contentAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Content Uploaded", Toast.LENGTH_SHORT).show()
        }
    }

    // Dummy method to simulate loading content
    private fun loadContentList() {
        // Example content, in real apps, fetch from the database
        contentList.add(ContentItem("Math PDF", "file://path/to/math.pdf", "Math"))
        contentList.add(ContentItem("Science Video", "file://path/to/science_video.mp4", "Science"))
        contentAdapter.notifyDataSetChanged()
    }

    companion object {
        const val UPLOAD_CONTENT_REQUEST_CODE = 1
    }
}

data class ContentItem(val name: String, val fileUri: String, val category: String)

// RecyclerView Adapter for Displaying Content Items
class ContentAdapter(private val contentList: List<ContentItem>) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    inner class ContentViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: android.widget.TextView = itemView.findViewById(android.R.id.text1)
        val categoryTextView: android.widget.TextView = itemView.findViewById(android.R.id.text2)
        val previewButton: android.widget.ImageButton = itemView.findViewById(android.R.id.button1)
        val editButton: android.widget.ImageButton = itemView.findViewById(android.R.id.button2)
        val deleteButton: android.widget.ImageButton = itemView.findViewById(android.R.id.button3)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ContentViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val content = contentList[position]
        holder.nameTextView.text = content.name
        holder.categoryTextView.text = content.category

        // Handle preview button click
        holder.previewButton.setOnClickListener {
            // Open content preview (e.g., for PDFs or videos)
            Toast.makeText(holder.itemView.context, "Previewing ${content.name}", Toast.LENGTH_SHORT).show()
        }

        // Handle edit button click
        holder.editButton.setOnClickListener {
            // Open an activity or dialog to edit the content (e.g., replace the file or update name/category)
            Toast.makeText(holder.itemView.context, "Editing ${content.name}", Toast.LENGTH_SHORT).show()
        }

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            // Confirm deletion and remove content
            (holder.itemView.context as ContentManagementActivity).deleteContent(content)
        }
    }

    override fun getItemCount(): Int = contentList.size

    // Delete content method
    private fun deleteContent(content: ContentItem) {
        // Remove content from list and update UI
        (contentList as MutableList).remove(content)
        notifyDataSetChanged()
        Toast.makeText(itemView.context, "${content.name} Deleted", Toast.LENGTH_SHORT).show()
    }
}
