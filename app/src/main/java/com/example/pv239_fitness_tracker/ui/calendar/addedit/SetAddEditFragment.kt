package com.example.pv239_fitness_tracker.ui.calendar.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pv239_fitness_tracker.databinding.FragmentSetAddEditBinding

class SetAddEditFragment : Fragment() {

    private lateinit var binding: FragmentSetAddEditBinding

    private val args: SetAddEditFragmentArgs by navArgs()

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