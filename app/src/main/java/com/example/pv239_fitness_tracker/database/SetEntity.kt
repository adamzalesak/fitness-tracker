package com.example.pv239_fitness_tracker.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
        entity = ActivityEntity::class,
        childColumns = ["activityId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.NO_ACTION,)
    ])
data class SetEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val activityId: Long,
    val weight: Double,
    val reps: Int,
)
