package com.example.pv239_fitness_tracker.ui.calendar.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pv239_fitness_tracker.MainActivity
import com.example.pv239_fitness_tracker.R
import com.example.pv239_fitness_tracker.databinding.FragmentSetAddEditBinding
import com.example.pv239_fitness_tracker.repository.ActivityRepository

class SetAddEditFragment : Fragment() {

    private lateinit var binding: FragmentSetAddEditBinding

    private val args: SetAddEditFragmentArgs by navArgs()
    private val activityRepository: ActivityRepository by lazy {
        ActivityRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetAddEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setToolbarTitle(getString(R.string.add_set))

        setInitialValues()

        binding.saveButton.setOnClickListener {
            val weightText = binding.weightEditText.text.toString()
            val repsText = binding.repsEditText.text.toString()

            if (weightText.isEmpty()) {
                binding.weightEditText.error = getString(R.string.required_value_validation_error)
            } else if (repsText.isEmpty()) {
                binding.repsEditText.error = getString(R.string.required_value_validation_error)
            } else {
                activityRepository.addOrUpdateSet(
                    id = args.set?.id,
                    weight = weightText.toDouble(),
                    reps = repsText.toInt(),
                    activityId = args.activityId,
                )
                findNavController().navigateUp()
            }

        }
    }

    private fun setInitialValues() {
        val set = args.set

        if (set != null) {
            (activity as MainActivity).setToolbarTitle(getString(R.string.edit_set))
            binding.repsEditText.setText(set.reps.toString())
            binding.weightEditText.setText(set.weight.toString())
        }
    }

}