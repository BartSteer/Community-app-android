package com.example.communityapp.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.communityapp.databinding.ItemProfileSectionBinding

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    private var name: String = "John Doe"
    private var email: String = "john.doe@example.com"
    private var bio: String = "This is my bio."
    private var points: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemProfileSectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(name, email, bio, points)
    }

    override fun getItemCount(): Int = 1

    fun updateName(newName: String) {
        this.name = newName
        notifyItemChanged(0)
    }

    fun updateEmail(newEmail: String) {
        this.email = newEmail
        notifyItemChanged(0)
    }

    fun updateBio(newBio: String) {
        this.bio = newBio
        notifyItemChanged(0)
    }

    fun updatePoints(newPoints: Int) {
        this.points = newPoints
        notifyItemChanged(0)
    }

    class ProfileViewHolder(private val binding: ItemProfileSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, email: String, bio: String, points: Int) {
            binding.profileName.text = name
            binding.profileEmail.text = email
            binding.pointsText.text = "$points points"
        }
    }
}
