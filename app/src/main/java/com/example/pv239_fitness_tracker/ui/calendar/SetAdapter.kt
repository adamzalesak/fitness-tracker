package com.example.pv239_fitness_tracker.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.data.Set
import com.example.pv239_fitness_tracker.databinding.ItemWorkoutSetBinding

class SetAdapter(private val sets: List<Set>) : RecyclerView.Adapter<SetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder =
        SetViewHolder(
            ItemWorkoutSetBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.bind(sets[position])
    }

    override fun getItemCount() = sets.size
}

class SetViewHolder(
    private val binding: ItemWorkoutSetBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Set) {
        binding.weightAmountTextView.text = item.weight.toString()
        binding.repsAmountTextView.text = item.reps.toString()
    }
}