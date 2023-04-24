package com.example.pv239_fitness_tracker.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.FragmentExercisesBinding


class ExercisesFragment : Fragment() {
    private lateinit var binding: FragmentExercisesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exerciseList = listOf(
            Exercise(1, "Exercise 1"),
            Exercise(2, "Exercise 2"),
            Exercise(3, "Exercise 3"),
            Exercise(4, "Exercise 4"),
            Exercise(5, "Exercise 5"),
            Exercise(6, "Exercise 6"),
            Exercise(7, "Exercise 7"),
            Exercise(8, "Exercise 8"),
            Exercise(9, "Exercise 9"),
            Exercise(10, "Exercise 10"),
            Exercise(11, "Exercise 11"),
            Exercise(12, "Exercise 12"),
            Exercise(13, "Exercise 13"),
            Exercise(14, "Exercise 14"),
            Exercise(15, "Exercise 15"),
        )

        val adapter = ExercisesAdapter(exerciseList)
        binding.listExercises.layoutManager = LinearLayoutManager(context)
        binding.listExercises.adapter = adapter

        binding.addExerciseButton.setOnClickListener {
            findNavController()
                .navigate(
                    ExercisesFragmentDirections.actionExercisesFragmentToFragmentExerciseAdd()
                )
        }
    }
}

