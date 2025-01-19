package com.example.communityapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.communityapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupFab()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = EventsAdapter(getSampleEvents())
        binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.postsRecyclerView.adapter = adapter
    }

    private fun setupFab() {
        binding.createPostFab.setOnClickListener {
            val bottomSheet = CreateEventBottomSheet { newEvent ->
                adapter.addEvent(newEvent)
            }
            bottomSheet.show(parentFragmentManager, "CreateEventBottomSheet")
        }
    }

    private fun getSampleEvents(): MutableList<Event> {
        return mutableListOf(
            Event("John Doe", "19 Jan 2025 at 10:13", "Going out for drinks.", 10),
            Event("Jane Smith", "18 Jan 2025 at 15:00", "Looking for hiking partners.", 20)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Event(val userName: String, val date: String, val description: String, val points: Int)
