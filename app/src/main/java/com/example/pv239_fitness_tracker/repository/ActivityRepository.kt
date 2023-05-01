package com.example.pv239_fitness_tracker.repository

import android.content.Context
import com.example.pv239_fitness_tracker.data.Activity
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.data.Set
import com.example.pv239_fitness_tracker.database.ActivityDao
import com.example.pv239_fitness_tracker.database.ActivityDatabase
import com.example.pv239_fitness_tracker.database.ExerciseDao
import com.example.pv239_fitness_tracker.database.SetDao
import com.example.pv239_fitness_tracker.repository.mappers.toAppData
import com.example.pv239_fitness_tracker.repository.mappers.toEntity
import com.example.pv239_fitness_tracker.util.DateUtil
import java.time.LocalDate

class ActivityRepository (
    context: Context,
    private val database: ActivityDatabase = ActivityDatabase.create(context),
    private val activityDao: ActivityDao = database.activityDao(),
    private val setDao: SetDao = database.setDao(),
    private val exerciseDao: ExerciseDao = database.exerciseDao(),
) {

    fun deleteActivity(activity: Activity) {
        //Delete all sets
        for (set in setDao.selectSetsForActivity(activity.id)) {
            setDao.delete(set)
        }
        //Delete activity
        activityDao.delete(activity.toEntity())
    }

    fun addActivity(exercise: Exercise, date: LocalDate) {
        val activity = Activity(
            id = 0,
            exercise = exercise,
            date = date,
            sets = emptyList(),
        )
        activityDao.persist(activity.toEntity())
    }

    fun deleteSet(set: Set, activityId: Long) {
        setDao.delete(set.toEntity(activityId))
    }

    fun addOrUpdateSet(weight: Double, reps: Int, activityId: Long, id: Long? = null) {
        val set = Set(
            id = id ?: 0,
            weight = weight,
            reps = reps,
        )
        setDao.persist(set.toEntity(activityId))
    }

    fun getActivitiesForDate(date: LocalDate): List<Activity> =
        activityDao.selectActivitiesForDate(DateUtil.dateFormat.format(date))
            .map { it.toAppData(setDao.selectSetsForActivity(it.id), exerciseDao.selectExerciseById(it.exerciseId)) }

    fun saveAll(data: List<Activity>) {
        for (activity in data) {
            activityDao.persist(activity.toEntity())
            setDao.persist(activity.sets.map { it.toEntity(activity.id) })
        }
    }

    fun getActivitiesForExercise(id: Long): List<Activity> =
        activityDao.selectActivitiesForExercise(id)
            .map { it.toAppData(setDao.selectSetsForActivity(it.id), exerciseDao.selectExerciseById(it.exerciseId)) }

    fun getDefaultExercise(): Exercise =
        exerciseDao.selectExerciseById(1).toAppData()
}