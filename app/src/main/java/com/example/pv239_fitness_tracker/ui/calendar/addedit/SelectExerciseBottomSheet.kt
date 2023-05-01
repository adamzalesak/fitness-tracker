package com.example.pv239_fitness_tracker.ui.calendar.addedit

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pv239_fitness_tracker.R
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.BottomSheetExerciseSelectBinding
import com.example.pv239_fitness_tracker.repository.ExerciseRepository
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectExerciseBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetExerciseSelectBinding

    private val args: SelectExerciseBottomSheetArgs by navArgs()

    private val exerciseRepository: ExerciseRepository by lazy {
        ExerciseRepository(requireContext())
    }

    private val adapter =
        ExerciseBottomSheetAdapter(
            onSelect = { exercise: Exercise ->
                findNavController()
                    .navigate(
                        SelectExerciseBottomSheetDirections.actionExercisesBottomSheetToAddActivityFragment(args.date, exercise)
                    )
            }
        )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetExerciseSelectBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exercisesRecycler.layoutManager = LinearLayoutManager(context)
        binding.exercisesRecycler.adapter = adapter
        adapter.submitList(exerciseRepository.getAllExercises())
    }
}