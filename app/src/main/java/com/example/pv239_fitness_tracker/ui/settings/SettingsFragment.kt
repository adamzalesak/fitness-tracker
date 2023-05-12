package com.example.pv239_fitness_tracker.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.pv239_fitness_tracker.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}