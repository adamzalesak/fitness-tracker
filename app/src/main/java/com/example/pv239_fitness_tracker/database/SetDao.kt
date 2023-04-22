package com.example.pv239_fitness_tracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: SetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun persist(entity: List<SetEntity>)

    @Query("SELECT * FROM SetEntity WHERE activityId = :id")
    fun selectSetsForActivity(id: Long): List<SetEntity>
}