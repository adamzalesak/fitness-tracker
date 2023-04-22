package com.example.pv239_fitness_tracker.data

import java.time.LocalDate

data class Activity(
    val id: Long,
    val exercise: Exercise,
    val date: LocalDate,
    val sets: List<Set>,
)
