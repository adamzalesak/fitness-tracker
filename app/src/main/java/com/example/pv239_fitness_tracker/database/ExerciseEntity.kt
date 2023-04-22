package com.example.pv239_fitness_tracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
)