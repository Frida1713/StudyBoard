package com.example.studyapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginLink: TextView
    private lateinit var passwordToggleButton: ImageButton
    private lateinit var roleRadioGroup: RadioGroup // RadioGroup for User/Admin selection
    private lateinit var radioUser: RadioButton // RadioButton for User
    private lateinit var radioAdmin: RadioButton // RadioButton for Admin

    private val auth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    private var passwordVisible = false  // Flag for password visibility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize views
        fullNameEditText = findViewById(R.id.fullNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        registerButton = findViewById(R.id.registerButton)
        loginLink = findViewById(R.id.loginLink)
        passwordToggleButton = findViewById(R.id.passwordToggleButton)
        roleRadioGroup = findViewById(R.id.roleRadioGroup)
        radioUser = findViewById(R.id.radioUser)
        radioAdmin = findViewById(R.id.radioAdmin)

        // Toggle password visibility
        passwordToggleButton.setOnClickListener {
            togglePasswordVisibility()
        }

        // Register Button Click
        registerButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            // Validate input
            if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register the user
            registerUser(fullName, email, password)
        }

        // Login Link Click
        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()  // Close RegisterActivity
        }
    }

    private fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
        if (passwordVisible) {
            // Show password
            passwordEditText.transformationMethod = null
            confirmPasswordEditText.transformationMethod = null
            passwordToggleButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
        } else {
            // Hide password
            passwordEditText.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
            confirmPasswordEditText.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
            passwordToggleButton.setImageResource(android.R.drawable.ic_menu_view)
        }
    }

    private fun registerUser(fullName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Save the user in the database
                    val currentUser = auth.currentUser
                    currentUser?.let {
                        val userRef = database.child("users").child(it.uid)
                        val isAdmin = radioAdmin.isChecked
                        val user = User(fullName, email, isAdmin)
                        userRef.setValue(user).addOnCompleteListener { saveTask ->
                            if (saveTask.isSuccessful) {
                                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                navigateToDashboard(isAdmin)
                            } else {
                                Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
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
        finish()  // Close RegisterActivity
    }
}

data class User(val fullName: String, val email: String, val isAdmin: Boolean)
