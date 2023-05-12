package com.example.pv239_fitness_tracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: ExerciseEntity)

    @Update
    fun update(entity: ExerciseEntity)

    @Delete
    fun delete(entity: ExerciseEntity)

    @Query("SELECT * FROM ExerciseEntity WHERE id = :id")
    fun selectExerciseById(id: Long): ExerciseEntity

    @Query("SELECT * FROM ExerciseEntity ORDER BY name ASC")
    fun selectAllExercises(): List<ExerciseEntity>
}