package com.example.pv239_fitness_tracker.ui.calendar.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pv239_fitness_tracker.databinding.FragmentSetAddEditBinding
import com.example.pv239_fitness_tracker.repository.ActivityRepository

class SetAddEditFragment : Fragment() {

    private lateinit var binding: FragmentSetAddEditBinding

    private val args: SetAddEditFragmentArgs by navArgs()
    private val activityRepository: ActivityRepository by lazy {
        ActivityRepository(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSetAddEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialValues()

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            activityRepository.addOrUpdateSet(
                id = args.set?.id,
                weight = binding.weightEditText.text.toString().toDouble(),
                reps = binding.repsEditText.text.toString().toInt(),
                activityId = args.activityId,
            )
            findNavController().navigateUp()
        }
    }

    private fun setInitialValues() {
        val set = args.set

        if (set != null) {
            binding.repsEditText.setText(set.reps.toString())
            binding.weightEditText.setText(set.weight.toString())
        }
    }

}