package com.example.pv239_fitness_tracker.repository

import android.content.Context
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.database.ActivityDao
import com.example.pv239_fitness_tracker.database.ActivityDatabase
import com.example.pv239_fitness_tracker.database.ExerciseDao
import com.example.pv239_fitness_tracker.database.SetDao
import com.example.pv239_fitness_tracker.repository.mappers.toAppData
import com.example.pv239_fitness_tracker.repository.mappers.toEntity

class ExerciseRepository(
    context: Context,
    private val database: ActivityDatabase = ActivityDatabase.create(context),
    private val activityDao: ActivityDao = database.activityDao(),
    private val setDao: SetDao = database.setDao(),
    private val exerciseDao: ExerciseDao = database.exerciseDao(),
    private val activityRepository: ActivityRepository = ActivityRepository(context),
) {

    fun getAllExercises(): List<Exercise> =
        exerciseDao.selectAllExercises().map { it.toAppData() }

    fun addExercise(name: String) {
        val exercise = Exercise(
            id = 0,
            name = name,
        )
        exerciseDao.persist(exercise.toEntity())
    }

    fun updateExercise(exercise: Exercise) {
        exerciseDao.update(exercise.toEntity())
    }

    fun deleteExercise(exercise: Exercise) {
        for (activity in activityDao.selectActivitiesForExercise(exercise.id)) {
            activityRepository.deleteActivity(
                activity.toAppData(
                    setDao.selectSetsForActivity(
                        activity.id
                    ), exercise.toEntity()
                )
            )
        }

        exerciseDao.delete(exercise.toEntity())
    }


}