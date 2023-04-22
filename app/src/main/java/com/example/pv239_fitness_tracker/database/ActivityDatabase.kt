package com.example.pv239_fitness_tracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ExerciseEntity::class,
        SetEntity::class,
        ActivityEntity::class,
    ],
    version = 1,
)
abstract class ActivityDatabase : RoomDatabase() {

    companion object {
        private const val NAME = "fitness_tracker.db"

        fun create(context: Context): ActivityDatabase =
            Room.databaseBuilder(context, ActivityDatabase::class.java, NAME)
                .allowMainThreadQueries()
                .build()
    }

    abstract fun activityDao(): ActivityDao
    abstract fun setDao(): SetDao
}