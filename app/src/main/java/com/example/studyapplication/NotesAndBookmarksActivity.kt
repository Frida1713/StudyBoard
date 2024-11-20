package com.example.studyapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapplication.databinding.ActivityNotesAndBookmarksBinding
import com.google.firebase.firestore.FirebaseFirestore

class NotesAndBookmarksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesAndBookmarksBinding
    private lateinit var notesAdapter: NotesAdapter
    private val notesList = mutableListOf<Note>()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesAndBookmarksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter(notesList)
        binding.recyclerView.adapter = notesAdapter

        // Add new note
        binding.addNoteButton.setOnClickListener {
            val noteText = binding.noteInput.text.toString()
            if (noteText.isNotEmpty()) {
                val note = Note(noteText)
                saveNoteToDatabase(note)
                binding.noteInput.text.clear()
            } else {
                Toast.makeText(this, "Note cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }

        // Fetch notes from Firestore (or a local database)
        fetchNotesFromDatabase()
    }

    private fun saveNoteToDatabase(note: Note) {
        // Save the note in Firestore for syncing across devices
        firestore.collection("notes")
            .add(note)
            .addOnSuccessListener {
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                fetchNotesFromDatabase() // Refresh notes list
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error adding note", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchNotesFromDatabase() {
        firestore.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                notesList.clear()
                for (document in result) {
                    val note = document.toObject(Note::class.java)
                    notesList.add(note)
                }
                notesAdapter.notifyDataSetChanged()
            }
    }
}

data class Note(
    val text: String = "", // Store text of the note
    val timestamp: Long = System.currentTimeMillis() // Time when the note was created
)

class NotesAdapter(private val notesList: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val noteTextView: android.widget.TextView = itemView.findViewById(android.R.id.text1)
        val timestampTextView: android.widget.TextView = itemView.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): NoteViewHolder {
        val view = android.view.LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]
        holder.noteTextView.text = note.text
        holder.timestampTextView.text = java.text.SimpleDateFormat("MM/dd/yyyy HH:mm").format(note.timestamp)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}

data class Bookmark(
    val title: String,
    val description: String,
    val section: String, // Could be a page number or video timestamp
    val timestamp: Long = System.currentTimeMillis()
)



