package com.example.pv239_fitness_tracker.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.data.Activity
import com.example.pv239_fitness_tracker.databinding.ItemWorkoutActivityBinding

class ActivityAdapter(
    private val onItemClick: (Activity) -> Unit,
) : ListAdapter<Activity, ActivityViewHolder>(ActivityDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder =
        ActivityViewHolder(
            ItemWorkoutActivityBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }

}

class ActivityViewHolder(
    private val binding: ItemWorkoutActivityBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Activity, onItemClick: (Activity) -> Unit) {
        binding.exerciseNameTextView.text = item.exercise.name

        binding.root.setOnClickListener {
            onItemClick(item)
        }
    }
}

class ActivityDiffUtil : DiffUtil.ItemCallback<Activity>() {
    override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean =
        oldItem == newItem

}