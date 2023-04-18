package com.example.pv239_fitness_tracker.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.data.Activity
import com.example.pv239_fitness_tracker.data.Set
import com.example.pv239_fitness_tracker.databinding.ItemWorkoutActivityBinding

class ActivityAdapter(private val activities: List<Activity>) : RecyclerView.Adapter<ActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder =
        ActivityViewHolder(
            ItemWorkoutActivityBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount() = activities.size
}

class ActivityViewHolder(
    private val binding: ItemWorkoutActivityBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Activity) {
        binding.exerciseNameTextView.text = item.exercise.name

        val setAdapter = SetAdapter(item.sets)
        binding.setRecycler.layoutManager = LinearLayoutManager(itemView.context)
        binding.setRecycler.adapter = setAdapter
    }
}