package com.example.pv239_fitness_tracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: ActivityEntity)

    @Query("SELECT * FROM ActivityEntity WHERE date = :date")
    fun selectActivitiesForDate(date: String): List<ActivityEntity>

}