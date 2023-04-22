package com.example.pv239_fitness_tracker.ui.calendar

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pv239_fitness_tracker.data.Set
import com.example.pv239_fitness_tracker.databinding.FragmentCalendarBinding
import com.example.pv239_fitness_tracker.repository.ActivityRepository
import com.example.pv239_fitness_tracker.util.DateUtil
import java.time.LocalDate

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

    private fun refreshList() {
        binding.currentDate.text = selectedDate.format(DateUtil.dateFormat)
        adapter.submitList(activityRepository.getActivitiesForDate(selectedDate))
    }

    private val adapter: ActivityAdapter by lazy {
        ActivityAdapter(
            onActivityDelete = { activity ->
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Do you want to delete this activity?")
                builder.setPositiveButton("Yes") { dialog, _ ->
                    activityRepository.deleteActivity(activity)
                    refreshList()
                    dialog.cancel()
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                val dialog = builder.create()
                dialog.setTitle("Delete this activity")
                dialog.show()
            },
            onSetAdd = { activityId: Long ->
                findNavController()
                    .navigate(
                        CalendarFragmentDirections.actionSetFragmentToSetAddEditFragment(activityId = activityId))
            },
            onSetClick = { activityId: Long, set: Set ->
                findNavController()
                    .navigate(CalendarFragmentDirections.actionSetFragmentToSetAddEditFragment(activityId = activityId, set = set))
            }
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        refreshList()

        binding.prevDay.setOnClickListener {
            selectedDate = selectedDate.minusDays(1)
            refreshList()
        }

        binding.nextDay.setOnClickListener {
            selectedDate = selectedDate.plusDays(1)
            refreshList()
        }

        binding.currentDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),
                {_, year, month, dayOfMonth ->
                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    refreshList()
                },
                selectedDate.year,
                selectedDate.monthValue - 1,
                selectedDate.dayOfMonth
            )

            datePickerDialog.show()
        }

        binding.addActivityButton.setOnClickListener {
            findNavController()
                .navigate(
                    CalendarFragmentDirections.actionActivityFragmentToActivityAddFragment(date = selectedDate))
        }
    }
}
