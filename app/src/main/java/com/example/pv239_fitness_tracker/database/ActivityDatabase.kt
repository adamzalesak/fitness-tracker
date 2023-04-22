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

        fun create(context: Context): ActivityDatabase {
            val database = Room.databaseBuilder(context, ActivityDatabase::class.java, NAME)
                .allowMainThreadQueries()
                .build()

            //Seed basic exercises
            database.exerciseDao().persist(ExerciseEntity(1, "Bench Press"))
            database.exerciseDao().persist(ExerciseEntity(2, "Squat"))
            database.exerciseDao().persist(ExerciseEntity(3, "Dead Lift"))

            return database
        }
    }

    abstract fun activityDao(): ActivityDao
    abstract fun setDao(): SetDao
    abstract fun exerciseDao(): ExerciseDao
}