package com.example.pv239_fitness_tracker.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.databinding.ItemStatisticsBinding

class StatisticsAdapter : ListAdapter<StatsData, StatisticsViewHolder>(StatisticsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder =
        StatisticsViewHolder(ItemStatisticsBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class StatisticsViewHolder(private val binding: ItemStatisticsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: StatsData) {
        binding.statisticsPeriodNameTextView.text = item.period
        binding.statisticsPeriodWeightTextView.text = String.format("%.1f", item.number) + " kg"
    }
}

class StatisticsDiffUtil : DiffUtil.ItemCallback<StatsData>() {
    override fun areContentsTheSame(oldItem: StatsData, newItem: StatsData): Boolean =
        oldItem.period == newItem.period


    override fun areItemsTheSame(oldItem: StatsData, newItem: StatsData): Boolean =
        oldItem.period == newItem.period

}