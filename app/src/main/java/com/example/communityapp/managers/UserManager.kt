package com.example.communityapp.managers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.communityapp.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserManager private constructor() {

    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("users")
    private var listenerRegistration: ListenerRegistration? = null

    private val _currentUser = MutableLiveData<UserModel?>()
    val currentUser: LiveData<UserModel?> get() = _currentUser

    companion object {
        val shared = UserManager()
    }

    fun startListening(userId: String) {
        stopListening()

        listenerRegistration = userCollection.document(userId).addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error listening for user updates: ${error.message}")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                _currentUser.value = snapshot.toObject(UserModel::class.java)
            }
        }
    }

    fun stopListening() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }

    fun updateUser(user: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                userCollection.document(user.id).set(user).await()
            } catch (e: Exception) {
                println("Error updating user: ${e.message}")
            }
        }
    }

    fun createUser(user: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                userCollection.document(user.id).set(user).await()
                startListening(user.id)
            } catch (e: Exception) {
                println("Error creating user: ${e.message}")
            }
        }
    }

    suspend fun getUser(userId: String): UserModel? {
        return try {
            val document = userCollection.document(userId).get().await()
            document.toObject(UserModel::class.java)
        } catch (e: Exception) {
            println("Error getting user: ${e.message}")
            null
        }
    }
}
