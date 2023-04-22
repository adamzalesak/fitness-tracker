package com.example.pv239_fitness_tracker.repository.mappers

import com.example.pv239_fitness_tracker.data.Activity
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.data.Set
import com.example.pv239_fitness_tracker.database.ActivityEntity
import com.example.pv239_fitness_tracker.database.SetEntity
import com.example.pv239_fitness_tracker.util.DateUtil
import java.time.LocalDate

fun SetEntity.toAppData(): Set =
    Set(
        id = id,
        weight = weight,
        reps = reps,
    )

fun Set.toEntity(activityId: Long): SetEntity =
    SetEntity(
        id = id,
        weight = weight,
        reps = reps,
        activityId = activityId
    )

fun ActivityEntity.toAppData(sets: List<SetEntity>): Activity =
    Activity(
        id = id,
        exercise = Exercise(exerciseId, "Bench Press"), //TODO select from exercise table
        date = LocalDate.parse(date, DateUtil.dateFormat),
        sets = sets.map { it.toAppData() }
    )

fun Activity.toEntity(): ActivityEntity =
    ActivityEntity(
        id = id,
        exerciseId = exercise.id,
        date = date.format(DateUtil.dateFormat),
    )

