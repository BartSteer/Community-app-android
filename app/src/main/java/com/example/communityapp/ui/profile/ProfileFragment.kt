package com.example.communityapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.communityapp.R
import com.example.communityapp.databinding.ItemProfileSectionBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        recyclerView = view.findViewById(R.id.profile_recycler_view)
        setupRecyclerView()
        observeViewModel()
        return view
    }

    private fun setupRecyclerView() {
        adapter = ProfileAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.user.collect { user ->
                adapter.setUser(user)
            }
        }

        lifecycleScope.launch {
            viewModel.currentPoints.collect { points ->
                adapter.setPoints(points)
            }
        }
    }
}

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
        }
    }
}
