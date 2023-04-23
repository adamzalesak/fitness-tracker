package com.example.pv239_fitness_tracker.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pv239_fitness_tracker.data.Activity
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.FragmentStatisticsBinding
import com.example.pv239_fitness_tracker.repository.ActivityRepository

class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var selectedExercise: Exercise

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

        binding.statisticsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = StatisticsAdapter()
        binding.statisticsRecycler.adapter = adapter

        val activities = activityRepository.getActivitiesForExercise(selectedExercise.id)
        val stats = mapToStatData(activities)
        adapter.submitList(stats)
    }

    private fun mapToStatData(activities: List<Activity>) : List<StatsData> {
        val groups = activities.groupBy { it.date.month }
        val data = groups.map { (k, v) -> StatsData(k.name, v.maxOf { activity -> activity.sets.maxOf { set -> set.weight} } ) }
        return data
    }
}