package com.example.pv239_fitness_tracker.util

import com.example.pv239_fitness_tracker.data.Activity

fun List<Activity>.maxWeight() : Double {
    if (this.isEmpty())
        return 0.0
    var max = 0.0
    this.forEach {
        if (it.sets.isNotEmpty()) {
            val setMax = it.sets.maxOf { set -> set.weight }
            if (setMax > max)
                max = setMax
        }
    }

    return max
}