package com.example.pv239_fitness_tracker.ui.calendar.addedit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.ItemExerciseBottomSheetBinding
import com.example.pv239_fitness_tracker.ui.exercises.ExerciseDiffUtil

class ExerciseBottomSheetAdapter(
    private val onSelect: (Exercise) -> Unit,
) : ListAdapter<Exercise, ExerciseViewHolder>(ExerciseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder =
        ExerciseViewHolder(
            ItemExerciseBottomSheetBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(item = getItem(position), onSelect = onSelect)
    }
}

class ExerciseViewHolder(
    private val binding: ItemExerciseBottomSheetBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Exercise, onSelect: (Exercise) -> Unit) {
        binding.exerciseNameTextView.text= item.name.toString()

        binding.root.setOnClickListener {
            onSelect(item)
        }
    }
}