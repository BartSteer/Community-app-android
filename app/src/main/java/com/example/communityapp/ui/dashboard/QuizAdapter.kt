package com.example.communityapp.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.communityapp.R

class QuizAdapter(private val quizzes: List<DashboardViewModel.Quiz>) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.quizTitle)
        val points: TextView = itemView.findViewById(R.id.quizPoints)
        val status: TextView = itemView.findViewById(R.id.quizStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.title.text = quiz.title
        holder.points.text = "${quiz.points} points"
        holder.status.text = if (quiz.isCompleted) "Completed" else ""
    }

    override fun getItemCount(): Int = quizzes.size
}
