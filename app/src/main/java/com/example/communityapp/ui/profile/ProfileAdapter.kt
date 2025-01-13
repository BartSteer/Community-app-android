package com.example.communityapp.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.communityapp.R
import com.example.communityapp.databinding.ItemProfileSectionBinding

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    private var user: UserModel = UserModel()
    private var points: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemProfileSectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(user, points)
    }

    override fun getItemCount(): Int = 1

    fun setUser(user: UserModel) {
        this.user = user
        notifyItemChanged(0)
    }

    fun setPoints(points: Int) {
        this.points = points
        notifyItemChanged(0)
    }

    class ProfileViewHolder(private val binding: ItemProfileSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserModel, points: Int) {
            binding.profileName.text = user.name
            binding.profileEmail.text = user.email
            binding.pointsText.text = "$points points"
            binding.profileImage.setImageResource(R.drawable.ic_profile_placeholder)
        }
    }
}
