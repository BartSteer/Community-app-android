package com.example.communityapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.communityapp.databinding.FragmentProfileImageEditBinding
import java.io.InputStream

class ProfileImageEditFragment : Fragment() {

    private var _binding: FragmentProfileImageEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by activityViewModels()

    private val galleryRequestCode = 1001
    private val cameraRequestCode = 1002

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileImageEditBinding.inflate(inflater, container, false)
        setupButtons()
        return binding.root
    }

    private fun setupButtons() {
        // Open Gallery
        binding.chooseFromGalleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, galleryRequestCode)
        }

        // Open Camera
        binding.takePhotoButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, cameraRequestCode)
        }

        // Save Button
        binding.saveButton.setOnClickListener {
            viewModel.profileImageBitmap.value?.let {
                viewModel.updateProfileImage(it)
            }
            viewModel.profileImageUri.value?.let {
                viewModel.updateProfileImageUri(it)
            }
            requireActivity().onBackPressed() // Navigate back to ProfileFragment
        }

        // Cancel Button
        binding.cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                galleryRequestCode -> {
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let { uri ->
                        displayImageFromUri(uri)
                        viewModel.updateProfileImageUri(uri.toString())
                    }
                }
                cameraRequestCode -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    binding.profileImagePreview.setImageBitmap(imageBitmap)
                    viewModel.updateProfileImage(imageBitmap)
                }
            }
        }
    }

    private fun displayImageFromUri(uri: Uri) {
        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        binding.profileImagePreview.setImageBitmap(bitmap)
        viewModel.updateProfileImage(bitmap)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
