package com.example.communityapp.ui.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val _name = MutableStateFlow("John Doe")
    val name: StateFlow<String> get() = _name

    private val _email = MutableStateFlow("john.doe@example.com")
    val email: StateFlow<String> get() = _email

    private val _bio = MutableStateFlow("This is my bio.")
    val bio: StateFlow<String> get() = _bio

    private val _currentPoints = MutableStateFlow(120)
    val currentPoints: StateFlow<Int> get() = _currentPoints

    val profileImageUri = MutableStateFlow<String?>(null)
    val profileImageBitmap = MutableStateFlow<Bitmap?>(null)

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updateBio(newBio: String) {
        _bio.value = newBio
    }

    fun updateProfileImageUri(uri: String) {
        profileImageUri.value = uri
    }

    fun updateProfileImage(bitmap: Bitmap) {
        profileImageBitmap.value = bitmap
    }

    fun refreshPoints(points: Int) {
        _currentPoints.value = points
    }
}
