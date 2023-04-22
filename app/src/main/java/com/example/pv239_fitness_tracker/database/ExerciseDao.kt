package com.example.pv239_fitness_tracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: ExerciseEntity)

    @Query("SELECT * FROM ExerciseEntity WHERE id = :id")
    fun selectExerciseById(id: Long): ExerciseEntity

    @Query("SELECT * FROM ExerciseEntity")
    fun selectAllExercises(): List<ExerciseEntity>

}