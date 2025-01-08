package com.example.communityapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _wrongUsername = MutableLiveData<Boolean>()
    val wrongUsername: LiveData<Boolean> get() = _wrongUsername

    private val _wrongPassword = MutableLiveData<Boolean>()
    val wrongPassword: LiveData<Boolean> get() = _wrongPassword

    fun authenticateUser(username: String, password: String) {
        if (username.lowercase() == "mario2021") {
            if (password.lowercase() == "abc123") {
                _wrongUsername.value = false
                _wrongPassword.value = false
            } else {
                _wrongPassword.value = true
            }
        } else {
            _wrongUsername.value = true
        }
    }
}

