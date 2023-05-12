package com.example.pv239_fitness_tracker.repository

import android.content.Context
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.database.ActivityDao
import com.example.pv239_fitness_tracker.database.ActivityDatabase
import com.example.pv239_fitness_tracker.database.ExerciseDao
import com.example.pv239_fitness_tracker.database.SetDao
import com.example.pv239_fitness_tracker.repository.mappers.toAppData
import com.example.pv239_fitness_tracker.repository.mappers.toEntity
import com.example.pv239_fitness_tracker.util.DateUtil
import java.lang.Double.max
import java.time.LocalDate

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

    fun getExerciseById(id: Long): Exercise? =
        exerciseDao.selectExerciseById(id)?.toAppData()

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

    fun getMostImprovedExercise(): Exercise? {
        val exercises = exerciseDao.selectAllExercises()
        var mostImprovedExercise: Exercise? = null
        var maxPercentageImprovement = 0.0
        for (exercise in exercises) {
            val activities = activityDao.selectActivitiesForExercise(exercise.id)
            var maxWeightInLastWeek = 0.0
            var maxWeightBefore = 0.0

            for (activity in activities.filter {
                LocalDate.parse(
                    it.date,
                    DateUtil.dateFormat
                ) >= LocalDate.now().minusDays(7)
            }) {
                val sets = setDao.selectSetsForActivity(activity.id)
                for (set in sets) {
                    maxWeightInLastWeek = max(maxWeightInLastWeek, set.weight * set.reps)
                }
            }

            for (activity in activities.filter {
                LocalDate.parse(
                    it.date,
                    DateUtil.dateFormat
                ) < LocalDate.now().minusDays(7)
            }) {
                val sets = setDao.selectSetsForActivity(activity.id)
                for (set in sets) {
                    maxWeightBefore = max(maxWeightBefore, set.weight * set.reps)
                }
            }

            val percentageImprovement = (maxWeightInLastWeek - maxWeightBefore) / maxWeightBefore
            if (percentageImprovement > maxPercentageImprovement) {
                maxPercentageImprovement = percentageImprovement
                mostImprovedExercise = exercise.toAppData()
            }
        }
        return mostImprovedExercise
    }

    fun getNumberOfActivitiesInLastWeek(): Int {
        val exercises = exerciseDao.selectAllExercises()
        var count = 0
        for (exercise in exercises) {
            val activities = activityDao.selectActivitiesForExercise(exercise.id)
            count += (activities.filter {
                LocalDate.parse(
                    it.date,
                    DateUtil.dateFormat
                ) >= LocalDate.now().minusDays(7)
            }).count()

        }
        return count
    }
}