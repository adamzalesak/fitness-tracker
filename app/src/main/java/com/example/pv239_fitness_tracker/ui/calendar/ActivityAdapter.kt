package com.example.pv239_fitness_tracker.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.data.Activity
import com.example.pv239_fitness_tracker.data.Set
import com.example.pv239_fitness_tracker.databinding.ItemWorkoutActivityBinding

class ActivityAdapter(
    private val onActivityDelete: (Activity) -> Unit,
    private val onActivityClick: (Activity) -> Unit,
    private val onSetAdd: (Long) -> Unit,
    private val onSetClick: (Long, Set) -> Unit,
    private val onSetDelete: (Long, Set) -> Unit,
) : ListAdapter<Activity, ActivityViewHolder>(ActivityDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder =
        ActivityViewHolder(
            ItemWorkoutActivityBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(
            item = getItem(position),
            onActivityClick = onActivityClick,
            onActivityDelete = onActivityDelete,
            onSetAdd = onSetAdd,
            onSetClick = onSetClick,
            onSetDelete = onSetDelete,
        )
    }
}

class ActivityViewHolder(
    private val binding: ItemWorkoutActivityBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Activity,
             onActivityClick: (Activity) -> Unit,
             onActivityDelete: (Activity) -> Unit,
             onSetAdd: (Long) -> Unit,
             onSetClick: (Long, Set) -> Unit,
             onSetDelete: (Long, Set) -> Unit,
    ) {
        binding.exerciseNameTextView.text = item.exercise.name

        binding.root.setOnClickListener {
            onActivityClick(item)
        }

        binding.activityDelete.setOnClickListener {
            onActivityDelete(item)
        }

        val setAdapter = SetAdapter(
            activityId = item.id,
            onSetClick = onSetClick,
            onSetDelete = onSetDelete,
        )

        binding.setRecycler.layoutManager = LinearLayoutManager(itemView.context)
        binding.setRecycler.adapter = setAdapter

        binding.addSetButton.setOnClickListener {
            onSetAdd(item.id)
        }

        setAdapter.submitList(item.sets)
    }
}

class ActivityDiffUtil : DiffUtil.ItemCallback<Activity>() {
    override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean =
        oldItem == newItem
}