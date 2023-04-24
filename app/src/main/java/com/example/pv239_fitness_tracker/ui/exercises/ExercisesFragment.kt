package com.example.pv239_fitness_tracker.ui.exercises

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pv239_fitness_tracker.R
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.FragmentExercisesBinding
import com.example.pv239_fitness_tracker.repository.ExerciseRepository


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

    private val exerciseRepository: ExerciseRepository by lazy {
        ExerciseRepository(requireContext())
    }

    private fun refreshList() {
        adapter.submitList(exerciseRepository.getAllExercises())
    }

    private val adapter =
        ExercisesAdapter(
            onExerciseClick = { exercise: Exercise ->
                findNavController()
                    .navigate(
                        ExercisesFragmentDirections.actionExercisesFragmentToFragmentExerciseAddEdit(
                            exercise
                        )
                    )
            },
            onExerciseDelete = { exercise: Exercise ->
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.delete_exercise_message))
                builder.setPositiveButton(getString(R.string.delete_exercise_positive_button)) { dialog, _ ->
                    exerciseRepository.deleteExercise(exercise)
                    refreshList()
                    dialog.cancel()
                }
                builder.setNegativeButton(getString(R.string.delete_exercise_negative_button)) { dialog, _ ->
                    dialog.cancel()
                }
                val dialog = builder.create()
                dialog.setTitle(getString(R.string.delete_exercise_title))
                dialog.show()
            }
        )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listExercises.layoutManager = LinearLayoutManager(context)
        binding.listExercises.adapter = adapter

        refreshList()

        binding.addExerciseButton.setOnClickListener {
            findNavController()
                .navigate(
                    ExercisesFragmentDirections.actionExercisesFragmentToFragmentExerciseAddEdit()
                )
        }
    }
}

