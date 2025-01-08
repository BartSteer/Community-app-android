package com.example.communityapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.communityapp.MainActivity
import com.example.communityapp.R
import com.example.communityapp.ui.login.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)  // Use the new layout file

        supportActionBar?.hide()
        // Initialize ViewModel
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Find views using findViewById
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)

        // Observe changes in ViewModel
        loginViewModel.wrongUsername.observe(this, { isWrongUsername ->
            if (isWrongUsername) {
                usernameEditText.error = "Incorrect username"
            }
        })

        loginViewModel.wrongPassword.observe(this, { isWrongPassword ->
            if (isWrongPassword) {
                passwordEditText.error = "Incorrect password"
            }
        })

        // Handle login button click
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginViewModel.authenticateUser(username, password)

            if (!loginViewModel.wrongUsername.value!! && !loginViewModel.wrongPassword.value!!) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Close LoginActivity
            }
        }
    }
}
