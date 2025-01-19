package com.example.communityapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    // Inline Quiz data class
    data class Quiz(val title: String, val points: Int, val isCompleted: Boolean)

    private val _quizzes = MutableLiveData<MutableList<Quiz>>().apply {
        value = mutableListOf(
            Quiz("What is the best national dish Bulgaria has?", 10, false),
            Quiz("What is banitsa?", 15, true)
        )
    }

    val quizzes: LiveData<MutableList<Quiz>> = _quizzes

    fun addQuiz(quiz: Quiz) {
        _quizzes.value?.add(quiz)
        _quizzes.postValue(_quizzes.value)
    }
}
