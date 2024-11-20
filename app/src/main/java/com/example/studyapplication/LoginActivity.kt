package com.example.studyapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView  // Reference to register link TextView
    private lateinit var roleRadioGroup: RadioGroup  // Reference to RadioGroup for role selection
    private lateinit var radioUser: RadioButton  // RadioButton for User role
    private lateinit var radioAdmin: RadioButton  // RadioButton for Admin role

    private val auth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerLink = findViewById(R.id.registerLink)
        roleRadioGroup = findViewById(R.id.roleRadioGroup)
        radioUser = findViewById(R.id.radioUser)
        radioAdmin = findViewById(R.id.radioAdmin)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill out both fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Register Link Click - Navigate to RegisterActivity
        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()  // Close LoginActivity to prevent going back to it
        }
    }



    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    checkUserRole()
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkUserRole() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userRef = database.child("users").child(currentUser.uid)
            userRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val isAdmin = snapshot.child("isAdmin").getValue(Boolean::class.java) ?: false
                    navigateToDashboard(isAdmin)
                } else {
                    Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToDashboard(isAdmin: Boolean) {
        val intent = if (isAdmin) {
            Intent(this, AdminDashboardActivity::class.java)
        } else {
            Intent(this, UserDashboardActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
