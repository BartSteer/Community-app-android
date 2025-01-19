package com.example.communityapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.communityapp.R

class EventsAdapter(private val events: MutableList<Event>) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.userName)
        val date: TextView = view.findViewById(R.id.postDate)
        val description: TextView = view.findViewById(R.id.postDescription)
        val points: TextView = view.findViewById(R.id.postPoints)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.userName.text = event.userName
        holder.date.text = event.date
        holder.description.text = event.description
        holder.points.text = "${event.points} points"
    }

    override fun getItemCount(): Int = events.size

    fun addEvent(event: Event) {
        events.add(0, event) // Add the new event to the top
        notifyItemInserted(0)
    }
}
