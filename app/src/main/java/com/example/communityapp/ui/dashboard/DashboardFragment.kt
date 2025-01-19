package com.example.communityapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.communityapp.R
import com.example.communityapp.databinding.FragmentDashboardBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels()
    private lateinit var adapter: QuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupFab()
        observeQuizzes()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = QuizAdapter(viewModel.quizzes.value ?: emptyList())
        binding.quizzesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.quizzesRecyclerView.adapter = adapter
    }

    private fun setupFab() {
        binding.createQuizFab.setOnClickListener {
            showCreateQuizBottomSheet()
        }
    }

    private fun observeQuizzes() {
        viewModel.quizzes.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun showCreateQuizBottomSheet() {
        val bottomSheetView = layoutInflater.inflate(R.layout.create_quiz_bottom_sheet, null)
        val bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(bottomSheetView)

        bottomSheetView.findViewById<View>(R.id.saveQuizButton).setOnClickListener {
            val title = bottomSheetView.findViewById<EditText>(R.id.quizTitleInput).text.toString()
            val points =
                bottomSheetView.findViewById<EditText>(R.id.quizPointsInput).text.toString()
                    .toIntOrNull() ?: 0
            val isCompleted =
                bottomSheetView.findViewById<CheckBox>(R.id.quizCompletedCheckbox).isChecked

            if (title.isNotEmpty()) {
                viewModel.addQuiz(DashboardViewModel.Quiz(title, points, isCompleted))
                bottomSheet.dismiss()
            }
        }

        bottomSheet.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
