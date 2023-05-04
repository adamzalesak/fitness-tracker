package com.example.pv239_fitness_tracker.ui.calendar.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pv239_fitness_tracker.R
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.BottomSheetExerciseSelectBinding
import com.example.pv239_fitness_tracker.databinding.FragmentActivityAddBinding
import com.example.pv239_fitness_tracker.repository.ActivityRepository
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActivityAddFragment : Fragment() {

    private lateinit var binding: FragmentActivityAddBinding

    private val args: ActivityAddFragmentArgs by navArgs()
    private val activityRepository: ActivityRepository by lazy {
        ActivityRepository(requireContext())
    }

    private lateinit var exercise: Exercise

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        exercise = activityRepository.getDefaultExercise()
        binding = FragmentActivityAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.exerciseTextView.text = exercise.name
        binding.exerciseTextView.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val dialogBinding = BottomSheetExerciseSelectBinding.inflate(LayoutInflater.from(requireContext()))
            val adapter = ExerciseBottomSheetAdapter(
                onSelect = { e ->
                    exercise = e
                    binding.exerciseTextView.text = e.name
                    dialog.cancel()
                }
            )

            dialogBinding.exercisesRecycler.layoutManager = LinearLayoutManager(context)
            dialogBinding.exercisesRecycler.adapter = adapter
            adapter.submitList(activityRepository.getAllExercises())

            dialog.setContentView(dialogBinding.root)
            dialog.show()
        }

        binding.saveButton.setOnClickListener {
            activityRepository.addActivity(exercise, args.date)
            findNavController().navigateUp()
        }
    }
}