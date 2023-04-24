package com.example.pv239_fitness_tracker.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pv239_fitness_tracker.R
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.FragmentExerciseAddEditBinding
import com.example.pv239_fitness_tracker.repository.ExerciseRepository

class ExerciseAddEditFragment : Fragment() {

    private lateinit var binding: FragmentExerciseAddEditBinding
    private val args:
            ExerciseAddEditFragmentArgs by navArgs()
    private val exerciseRepository: ExerciseRepository by lazy {
        ExerciseRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseAddEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialValues()

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            if (args.exercise != null) {
                exerciseRepository.updateExercise(
                    Exercise(
                        args.exercise!!.id, binding.exerciseEditText.text.toString()
                    )
                )
            } else {
                exerciseRepository.addExercise(binding.exerciseEditText.text.toString())
            }
            findNavController().navigateUp()
        }
    }

    private fun setInitialValues() {
        val exercise = args.exercise

        if (exercise != null) {
            binding.exerciseEditText.setText(exercise.name)
            binding.header.text = getString(R.string.edit_exercise)
            binding.saveButton.text = getString(R.string.edit_exercise)
        }
    }


}