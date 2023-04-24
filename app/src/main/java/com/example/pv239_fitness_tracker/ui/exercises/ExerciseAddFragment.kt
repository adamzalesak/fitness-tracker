package com.example.pv239_fitness_tracker.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pv239_fitness_tracker.databinding.FragmentExerciseAddBinding

class ExerciseAddFragment : Fragment() {

    private lateinit var binding: FragmentExerciseAddBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExerciseAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            // TODO: save to DB
            findNavController().navigateUp()
        }
    }



}