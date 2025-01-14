package com.example.communityapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.communityapp.R
import com.example.communityapp.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupProfileImageClick()
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
            }
        }

        lifecycleScope.launch {
            viewModel.currentPoints.collect { points ->
                adapter.setPoints(points)
            }
        }
    }

    private fun setupProfileImageClick() {
        // Navigate to Profile Image Edit Fragment when profile image is clicked
        binding.profileImage.setOnClickListener {
            findNavController().navigate(R.id.navigation_profile_image_edit)
        }
    }

    private fun setupButtons() {
        // Toggle editable form when "Edit Profile" is clicked
        binding.editProfileButton.setOnClickListener {
            toggleEditing(true)
        }

        // Save changes when "Save" is clicked
        binding.saveButton.setOnClickListener {
            // Update user details
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
