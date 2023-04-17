package com.example.pv239_fitness_tracker.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Activity(
    val id: Long,
    val exercise: Exercise,
    val date: Date,
    val sets: List<Set>,
) : Parcelable
