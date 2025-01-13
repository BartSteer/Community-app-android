package com.example.communityapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.communityapp.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var adapter: ProfileAdapter

    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupButtons()
        observeViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = ProfileAdapter()
        binding.profileRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.profileRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.user.collect { user ->
                adapter.setUser(user)
                if (!isEditing) {
                    // Populate editable fields for later
                    binding.editProfileName.setText(user.name)
                    binding.editProfileEmail.setText(user.email)
                    binding.editProfileBio.setText(user.bio)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.currentPoints.collect { points ->
                adapter.setPoints(points)
            }
        }
    }

    private fun setupButtons() {
        // Edit Profile Button
        binding.editProfileButton.setOnClickListener {
            toggleEditing(true)
        }

        // Save Button
        binding.saveButton.setOnClickListener {
            val updatedUser = UserModel(
                name = binding.editProfileName.text.toString(),
                email = binding.editProfileEmail.text.toString(),
                bio = binding.editProfileBio.text.toString()
            )
            viewModel.updateUser(updatedUser)
            toggleEditing(false)
        }
    }

    private fun toggleEditing(editing: Boolean) {
        isEditing = editing
        binding.profileRecyclerView.visibility = if (editing) View.GONE else View.VISIBLE
        binding.editableForm.visibility = if (editing) View.VISIBLE else View.GONE
        binding.editProfileButton.visibility = if (editing) View.GONE else View.VISIBLE
        binding.saveButton.visibility = if (editing) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
