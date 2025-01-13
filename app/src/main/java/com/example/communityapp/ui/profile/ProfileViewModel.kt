package com.example.communityapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _user = MutableStateFlow(UserModel())
    val user: StateFlow<UserModel> get() = _user

    private val _currentPoints = MutableStateFlow(0)
    val currentPoints: StateFlow<Int> get() = _currentPoints

    init {
        loadUser()
        refreshPoints()
    }

    private fun loadUser() {
        viewModelScope.launch {
            // Simulate loading user from a repository
            _user.value = UserModel(name = "John Doe", email = "john.doe@example.com", bio = "Bio here")
        }
    }

    private fun refreshPoints() {
        viewModelScope.launch {
            // Simulate fetching points
            _currentPoints.value = 120
        }
    }

    // Add updateUser method to allow updating user details
    fun updateUser(updatedUser: UserModel) {
        viewModelScope.launch {
            _user.value = updatedUser
        }
    }

    fun showSettings() {
        // Handle showing settings screen
    }
}

data class UserModel(
    val name: String = "",
    val email: String = "",
    val bio: String = ""
)
