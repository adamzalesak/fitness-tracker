package com.example.pv239_fitness_tracker.ui.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pv239_fitness_tracker.databinding.FragmentCalendarBinding
import com.example.pv239_fitness_tracker.repository.ActivityRepository
import com.example.pv239_fitness_tracker.util.DateUtil
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private var selectedDate = LocalDate.now()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private val activityRepository: ActivityRepository by lazy {
        ActivityRepository(requireContext())
    }

    private fun updateCurrentDate() {
        binding.currentDate.text = selectedDate.format(DateUtil.dateFormat)
    }

    private val adapter: ActivityAdapter by lazy {
        ActivityAdapter(
            onSetAdd = {
                findNavController()
                    .navigate(
                        CalendarFragmentDirections.actionSetFragmentToSetAddEditFragment()
                    )
            },
            onSetClick = { set ->
                findNavController()
                    .navigate(CalendarFragmentDirections.actionSetFragmentToSetAddEditFragment(set = set))
            }
        )
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

        adapter.submitList(activityRepository.getActivitiesForDate(selectedDate))

        binding.addActivityButton.setOnClickListener {
            findNavController()
                .navigate(
                    CalendarFragmentDirections.actionActivityFragmentToActivityAddFragment()
                )
        }
    }
}
