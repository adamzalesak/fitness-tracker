package com.example.pv239_fitness_tracker.ui.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pv239_fitness_tracker.data.Activity
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.data.Set
import com.example.pv239_fitness_tracker.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private var selectedDate = LocalDate.now()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateCurrentDate() {
        binding.currentDate.text = selectedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateCurrentDate()

        binding.prevDay.setOnClickListener {
            selectedDate = selectedDate.minusDays(1)
            updateCurrentDate()
        }

        binding.nextDay.setOnClickListener {
            selectedDate = selectedDate.plusDays(1)
            updateCurrentDate()
        }

        binding.currentDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),
                {view, year, month, dayOfMonth ->
                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    updateCurrentDate()
                },
                selectedDate.year,
                selectedDate.monthValue - 1,
                selectedDate.dayOfMonth
            )

            datePickerDialog.show()
        }

        val exercise1 = Exercise(1, "Bench")
        val exercise2 = Exercise(2, "Squat")
        val exercise3 = Exercise(3, "Deadlift")

        val sets = listOf(
            Set(1, 50.0, 10),
            Set(2, 60.0, 10),
            Set(3, 65.0, 8),
            Set(4, 70.0, 8),
        )

        val activityList = listOf(
            Activity(1, exercise1, Date(), sets),
            Activity(2, exercise2, Date(), sets.reversed()),
            Activity(3, exercise3, Date(), sets.shuffled()),
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = ActivityAdapter(activityList)
    }
}