package com.example.pv239_fitness_tracker.ui.calendar.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pv239_fitness_tracker.data.Exercise
import com.example.pv239_fitness_tracker.databinding.FragmentActivityAddBinding
import com.example.pv239_fitness_tracker.repository.ActivityRepository

class ActivityAddFragment : Fragment() {

    private lateinit var binding: FragmentActivityAddBinding

    private val args: ActivityAddFragmentArgs by navArgs()
    private val activityRepository: ActivityRepository by lazy {
        ActivityRepository(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentActivityAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            //TODO handle exercises? idk
            val activityExercise = binding.activityEditText.text.toString()

            activityRepository.addActivity(Exercise(1, "Bench Press"), args.date)
            findNavController().navigateUp()
        }
    }



}