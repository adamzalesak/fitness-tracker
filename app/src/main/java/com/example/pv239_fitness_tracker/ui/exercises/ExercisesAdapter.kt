package com.example.pv239_fitness_tracker.ui.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.R
import com.example.pv239_fitness_tracker.data.Exercise


class ExercisesAdapter(private val dataList: List<Exercise>) :
    RecyclerView.Adapter<ExercisesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExercisesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        val data = dataList[position]
        holder.titleTextView.text = data.name
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

class ExercisesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.exercise_item_name_text_view)
}
