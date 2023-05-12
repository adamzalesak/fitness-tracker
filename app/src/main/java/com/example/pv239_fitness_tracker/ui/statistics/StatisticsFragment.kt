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
import com.example.pv239_fitness_tracker.util.maxWeight
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.time.LocalDate

class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var selectedExercise: Exercise
    private lateinit var selectedActivities: List<Activity>
    private val args : StatisticsFragmentArgs by navArgs()

    private val activityRepository: ActivityRepository by lazy {
        ActivityRepository(requireContext())
    }

    private class MyXAxisFormatter : ValueFormatter() {
        private val days = arrayOf("Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days[value.toInt() - 1]
        }
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

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        selectedActivities = activityRepository.getActivitiesForExercise(selectedExercise.id)
        if (isEmptyExercise())
            return

        binding.lineChart.data = mapToGraphDataByMonth()
        binding.lineChart.xAxis.granularity = 1f
        binding.lineChart.invalidate()

        val stats = mapToStatDataByMonth()
        adapter.submitList(stats)
        binding.statisticsButtonMonthly.isEnabled = false
        binding.statisticsButtonMonthly.isClickable = false

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

    private fun isEmptyExercise() : Boolean {
        if (selectedActivities.isEmpty())
            return true
        val setCount = selectedActivities.map { activity -> activity.sets.count() }.sum()
        return setCount == 0
    }

    private fun mapToGraphDataByMonth() : LineData {
        val groups = selectedActivities.filter { activity -> activity.date.plusYears(1).isAfter(LocalDate.now()) }.groupBy { it.date.month }
        val entries = mutableListOf<Entry>()
        groups.iterator().forEach {
            val max = it.value.maxWeight()
            val x = it.key.value
            entries.add(Entry(x.toFloat(), max.toFloat()))
        }
        entries.sortBy { entry -> entry.x }
        binding.lineChart.xAxis.valueFormatter = null
        val dataSet = LineDataSet(entries, "Max")
        dataSet.axisDependency = AxisDependency.LEFT
        val datasets = mutableListOf<ILineDataSet>()
        datasets.add(dataSet)
        return LineData(datasets)
    }

    private fun mapToGraphDataByDay() : LineData {
        val groups = selectedActivities.filter { activity -> activity.date.plusMonths(1).isAfter(LocalDate.now()) }.groupBy { it.date.dayOfMonth }
        val entries = mutableListOf<Entry>()
        groups.iterator().forEach {
            val max = it.value.maxWeight()
            val x = it.key
            entries.add(Entry(x.toFloat(), max.toFloat()))
        }
        entries.sortBy { entry -> entry.x }
        binding.lineChart.xAxis.valueFormatter = null
        val dataSet = LineDataSet(entries, "Max")
        dataSet.axisDependency = AxisDependency.LEFT
        val datasets = mutableListOf<ILineDataSet>()
        datasets.add(dataSet)
        return LineData(datasets)
    }

    private fun mapToGraphDataByDayOfWeek() : LineData {
        val groups = selectedActivities.filter { activity -> activity.date.plusDays(7).isAfter(LocalDate.now()) }.groupBy { it.date.dayOfWeek }
        val entries = mutableListOf<Entry>()
        groups.iterator().forEach {
            val max = it.value.maxWeight()
            val x = it.key.value
            entries.add(Entry(x.toFloat(), max.toFloat()))
        }
        entries.sortBy { entry -> entry.x }
        binding.lineChart.xAxis.valueFormatter = MyXAxisFormatter()
        val dataSet = LineDataSet(entries, "Max")
        dataSet.axisDependency = AxisDependency.LEFT
        val datasets = mutableListOf<ILineDataSet>()
        datasets.add(dataSet)
        return LineData(datasets)
    }

    private fun mapToStatDataByMonth() : List<StatsData> {
        val groups = selectedActivities.filter { activity -> activity.date.plusYears(1).isAfter(LocalDate.now()) }.groupBy { it.date.month }
        val data = groups.map { (k, v) -> StatsData(k.name, v.maxWeight() ) }
        return data
    }

    private fun mapToStatDataByDayOfWeek() : List<StatsData> {
        val groups = selectedActivities.filter { activity -> activity.date.plusDays(7).isAfter(LocalDate.now()) }.groupBy { it.date.dayOfWeek }
        val data = groups.map { (k, v) -> StatsData(k.name, v.maxWeight() ) }
        return data
    }

    private fun mapToStatDataByDay() : List<StatsData> {
        val groups = selectedActivities.filter { activity -> activity.date.plusMonths(1).isAfter(LocalDate.now()) }.groupBy { it.date.dayOfMonth }
        val data = groups.map { (k, v) -> StatsData(k.toString(), v.maxWeight() ) }
        return data
    }

    private fun displayMonthlyStats() {
        val data = mapToStatDataByMonth()
        val adapter = StatisticsAdapter()
        val gData = mapToGraphDataByMonth()
        adapter.submitList(data)
        binding.statisticsRecycler.swapAdapter(adapter, true)
        binding.lineChart.data = gData
        binding.lineChart.invalidate()
        binding.statisticsButtonMonthly.isEnabled = false
        binding.statisticsButtonMonthly.isClickable = false
        binding.statisticsButtonDaily.isEnabled = true
        binding.statisticsButtonDaily.isClickable = true
        binding.statisticsButtonDayOfWeek.isEnabled = true
        binding.statisticsButtonDayOfWeek.isClickable = true
    }

    private fun displayDailyStats() {
        val data = mapToStatDataByDay()
        val adapter = StatisticsAdapter()
        val gData = mapToGraphDataByDay()
        adapter.submitList(data)
        binding.statisticsRecycler.swapAdapter(adapter, true)
        binding.lineChart.data = gData
        binding.lineChart.invalidate()
        binding.statisticsButtonMonthly.isEnabled = true
        binding.statisticsButtonMonthly.isClickable = true
        binding.statisticsButtonDaily.isEnabled = false
        binding.statisticsButtonDaily.isClickable = false
        binding.statisticsButtonDayOfWeek.isEnabled = true
        binding.statisticsButtonDayOfWeek.isClickable = true
    }

    private fun displayDayOfWeekStats() {
        val data = mapToStatDataByDayOfWeek()
        val adapter = StatisticsAdapter()
        val gData = mapToGraphDataByDayOfWeek()
        adapter.submitList(data)
        binding.statisticsRecycler.swapAdapter(adapter, true)
        binding.lineChart.data = gData
        binding.lineChart.invalidate()
        binding.statisticsButtonMonthly.isEnabled = true
        binding.statisticsButtonMonthly.isClickable = true
        binding.statisticsButtonDaily.isEnabled = true
        binding.statisticsButtonDaily.isClickable = true
        binding.statisticsButtonDayOfWeek.isEnabled = false
        binding.statisticsButtonDayOfWeek.isClickable = false
    }
}