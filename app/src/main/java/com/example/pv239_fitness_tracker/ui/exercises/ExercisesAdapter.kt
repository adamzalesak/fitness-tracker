package com.example.pv239_fitness_tracker.ui.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.ItemExerciseBinding


class ExercisesAdapter(
    private val onExerciseClick: (Exercise) -> Unit,
    private val onStatisticsClick: (Exercise) -> Unit,
    private val onExerciseDelete: (Exercise) -> Unit,
) : ListAdapter<Exercise, ExercisesViewHolder>(ExerciseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder =
        ExercisesViewHolder(
            ItemExerciseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        holder.bind(
            item = getItem(position),
            onExerciseClick = onExerciseClick,
            onStatisticsClick = onStatisticsClick,
            onExerciseDelete = onExerciseDelete,
        )
    }
}


class ExercisesViewHolder(
    private val binding: ItemExerciseBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Exercise,
        onExerciseClick: (Exercise) -> Unit,
        onStatisticsClick: (Exercise) -> Unit,
        onExerciseDelete: (Exercise) -> Unit
    ) {
        binding.exerciseItemNameTextView.text = item.name

        binding.root.setOnClickListener {
            onExerciseClick(item)
        }

        binding.exerciseItemStatisticsButton.setOnClickListener {
            onStatisticsClick(item)
        }

        binding.exerciseItemDeleteButton.setOnClickListener {
            onExerciseDelete(item)
        }
    }
}

class ExerciseDiffUtil : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean =
        oldItem == newItem
}