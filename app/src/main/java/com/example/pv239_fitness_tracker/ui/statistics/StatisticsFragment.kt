package com.example.pv239_fitness_tracker.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pv239_fitness_tracker.data.Activity
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.FragmentStatisticsBinding
import com.example.pv239_fitness_tracker.repository.ActivityRepository

class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var selectedExercise: Exercise
    private lateinit var selectedActivities: List<Activity>
    private val args : StatisticsFragmentArgs by navArgs()

    private val activityRepository: ActivityRepository by lazy {
        ActivityRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        selectedExercise = args.selectedExercise

        binding.statisticsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = StatisticsAdapter()
        binding.statisticsRecycler.adapter = adapter

        selectedActivities = activityRepository.getActivitiesForExercise(selectedExercise.id)
        val stats = mapToStatDataByMonth()
        adapter.submitList(stats)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.statisticsButtonDaily.setOnClickListener {
            displayDailyStats()
        }

        binding.statisticsButtonMonthly.setOnClickListener {
            displayMonthlyStats()
        }

        binding.statisticsButtonDayOfWeek.setOnClickListener {
            displayDayOfWeekStats()
        }
    }

    private fun mapToStatDataByMonth() : List<StatsData> {
        val groups = selectedActivities.groupBy { it.date.month }
        val data = groups.map { (k, v) -> StatsData(k.name, v.maxOf { activity -> activity.sets.maxOf { set -> set.weight} } ) }
        return data
    }

    private fun mapToStatDataByDayOfWeek() : List<StatsData> {
        val groups = selectedActivities.groupBy { it.date.dayOfWeek }
        val data = groups.map { (k, v) -> StatsData(k.name, v.maxOf { activity -> activity.sets.maxOf { set -> set.weight} } ) }
        return data
    }

    private fun mapToStatDataByDay() : List<StatsData> {
        val groups = selectedActivities.groupBy { it.date.dayOfMonth }
        val data = groups.map { (k, v) -> StatsData(k.toString(), v.maxOf { activity -> activity.sets.maxOf { set -> set.weight} } ) }
        return data
    }

    private fun displayMonthlyStats() {
        val data = mapToStatDataByMonth()
        val adapter = StatisticsAdapter()
        adapter.submitList(data)
        binding.statisticsRecycler.swapAdapter(adapter, true)
    }

    private fun displayDailyStats() {
        val data = mapToStatDataByDay()
        val adapter = StatisticsAdapter()
        adapter.submitList(data)
        binding.statisticsRecycler.swapAdapter(adapter, true)
    }

    private fun displayDayOfWeekStats() {
        val data = mapToStatDataByDayOfWeek()
        val adapter = StatisticsAdapter()
        adapter.submitList(data)
        binding.statisticsRecycler.swapAdapter(adapter, true)
    }
}