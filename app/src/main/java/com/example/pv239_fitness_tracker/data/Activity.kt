package com.example.pv239_fitness_tracker.data

import java.util.Date

data class Activity(
    val id: Long,
    val exercise: Exercise,
    val date: Date,
    val sets: List<Set>,
)
