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
import com.example.communityapp.R
import com.example.communityapp.databinding.FragmentProfileImageEditBinding
import java.io.InputStream

class ProfileImageEditFragment : Fragment() {

    private var _binding: FragmentProfileImageEditBinding? = null
    private val binding get() = _binding!!

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
            // Save logic here (e.g., save to local storage or backend)
            requireActivity().onBackPressed() // Navigate back after saving
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
                    // Handle gallery image
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let { uri ->
                        displayImageFromUri(uri)
                    }
                }
                cameraRequestCode -> {
                    // Handle camera image
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    binding.profileImagePreview.setImageBitmap(imageBitmap)
                }
            }
        }
    }

    private fun displayImageFromUri(uri: Uri) {
        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        binding.profileImagePreview.setImageBitmap(bitmap)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
