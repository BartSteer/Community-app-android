package com.example.communityapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.communityapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateEventBottomSheet(private val onEventCreated: (Event) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_create_event, container, false)

        val descriptionEditText: EditText = view.findViewById(R.id.eventDescription)
        val pointsEditText: EditText = view.findViewById(R.id.eventPoints)
        val createEventButton: View = view.findViewById(R.id.createEventButton)

        createEventButton.setOnClickListener {
            val description = descriptionEditText.text.toString()
            val points = pointsEditText.text.toString()

            if (description.isBlank() || points.isBlank()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val newEvent = Event(
                    userName = "You",
                    date = "Now",
                    description = description,
                    points = points.toInt()
                )
                onEventCreated(newEvent)
                dismiss() // Close the bottom sheet
            }
        }

        return view
    }
}
