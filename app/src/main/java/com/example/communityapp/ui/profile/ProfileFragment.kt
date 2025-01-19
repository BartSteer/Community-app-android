package com.example.communityapp.ui.profile

import android.net.Uri
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
            viewModel.name.collect { name ->
                binding.editProfileName.setText(name)
                adapter.updateName(name)
            }
        }

        lifecycleScope.launch {
            viewModel.email.collect { email ->
                binding.editProfileEmail.setText(email)
                adapter.updateEmail(email)
            }
        }

        lifecycleScope.launch {
            viewModel.bio.collect { bio ->
                binding.editProfileBio.setText(bio)
                adapter.updateBio(bio)
            }
        }

        lifecycleScope.launch {
            viewModel.currentPoints.collect { points ->
                adapter.updatePoints(points)
            }
        }

        lifecycleScope.launch {
            viewModel.profileImageUri.collect { uri ->
                if (!uri.isNullOrEmpty()) {
                    binding.profileImage.setImageURI(Uri.parse(uri))
                }
            }
        }

        lifecycleScope.launch {
            viewModel.profileImageBitmap.collect { bitmap ->
                bitmap?.let {
                    binding.profileImage.setImageBitmap(it)
                }
            }
        }
    }

    private fun setupProfileImageClick() {
        binding.profileImage.setOnClickListener {
            findNavController().navigate(R.id.navigation_profile_image_edit)
        }
    }

    private fun setupButtons() {
        binding.editProfileButton.setOnClickListener {
            toggleEditing(true)
        }

        binding.saveButton.setOnClickListener {
            // Save updated details directly into ViewModel
            viewModel.updateName(binding.editProfileName.text.toString())
            viewModel.updateEmail(binding.editProfileEmail.text.toString())
            viewModel.updateBio(binding.editProfileBio.text.toString())
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
