/*package com.example.communityapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.communityapp.R
import com.example.communityapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        usernameEditText = binding.username
        passwordEditText = binding.password

        loginViewModel.wrongUsername.observe(viewLifecycleOwner, Observer { isWrongUsername ->
            if (isWrongUsername) {
                usernameEditText.error = "Incorrect username"
            }
        })

        loginViewModel.wrongPassword.observe(viewLifecycleOwner, Observer { isWrongPassword ->
            if (isWrongPassword) {
                passwordEditText.error = "Incorrect password"
            }
        })

        // Handle Login Button Click
        binding.loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginViewModel.authenticateUser(username, password)

            // Check if the username and password are correct
            if (!loginViewModel.wrongUsername.value!! && !loginViewModel.wrongPassword.value!!) {
                // Navigate to HomeFragment
                findNavController().navigate(R.id.navigation_home)
            }
        }

        return binding.root
    }
}
*/