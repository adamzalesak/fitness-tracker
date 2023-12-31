package com.example.pv239_fitness_tracker.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.data.Activity
import com.example.pv239_fitness_tracker.data.Set
import com.example.pv239_fitness_tracker.databinding.ItemWorkoutSetBinding

class SetAdapter(
    private val activityId: Long,
    private val onSetClick: (Long, Set) -> Unit,
    private val onSetDelete: (Long, Set) -> Unit,
) : ListAdapter<Set, SetViewHolder>(SetDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder =
        SetViewHolder(
            ItemWorkoutSetBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.bind(
            item = getItem(position),
            activityId = activityId,
            onSetClick = onSetClick,
            onSetDelete = onSetDelete,
        )
    }
}

class SetViewHolder(
    private val binding: ItemWorkoutSetBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Set,
             activityId: Long,
             onSetClick: (Long, Set) -> Unit,
             onSetDelete: (Long, Set) -> Unit,
    ) {
        binding.weightAmountTextView.text = item.weight.toString()
        binding.repsAmountTextView.text = item.reps.toString()

        binding.root.setOnClickListener {
            onSetClick(activityId, item)
        }

        binding.deleteSet.setOnClickListener {
            onSetDelete(activityId, item)
        }
    }
}

class SetDiffUtil : DiffUtil.ItemCallback<Set>() {
    override fun areItemsTheSame(oldItem: Set, newItem: Set): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Set, newItem: Set): Boolean =
        oldItem == newItem
}