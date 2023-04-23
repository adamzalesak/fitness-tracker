package com.example.pv239_fitness_tracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: ActivityEntity)

    @Delete
    fun delete(entity: ActivityEntity)

    @Query("SELECT * FROM ActivityEntity WHERE date = :date")
    fun selectActivitiesForDate(date: String): List<ActivityEntity>

    @Query("SELECT * FROM ActivityEntity WHERE exerciseId = :id")
    fun selectActivitiesForExercise(id: Long): List<ActivityEntity>
}