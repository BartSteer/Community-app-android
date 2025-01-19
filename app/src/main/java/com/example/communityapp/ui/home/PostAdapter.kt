package com.example.communityapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.communityapp.databinding.ItemPostBinding

class PostsAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.userName.text = post.userName
            binding.postDate.text = post.date
            binding.postDescription.text = post.description
            binding.postPoints.text = "${post.points} points"
        }
    }

    // Data class for Post
    data class Post(
        val userName: String,
        val date: String,
        val description: String,
        val points: Int
    )
}
