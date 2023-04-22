package com.example.pv239_fitness_tracker.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = ExerciseEntity::class,
    childColumns = ["exerciseId"],
    parentColumns = ["id"],
    onDelete = NO_ACTION,)
])
data class ActivityEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val exerciseId: Long,
    val date: String,
)