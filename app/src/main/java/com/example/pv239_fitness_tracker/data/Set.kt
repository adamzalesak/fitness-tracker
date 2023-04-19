package com.example.pv239_fitness_tracker.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Set(
    val id: Long,
    val weight: Double,
    val reps: Int,
) : Parcelable
