package com.example.pv239_fitness_tracker.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val id: Long,
    val name: String,
) : Parcelable