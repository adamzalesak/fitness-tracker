package com.example.pv239_fitness_tracker.ui.settings

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.internal.PreferenceImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pv239_fitness_tracker.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get current primary color according to theme and set it to preference icons
        val color =
            if (requireContext().resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                ContextCompat.getColor(requireContext(), R.color.purple_200)
            } else {
                ContextCompat.getColor(requireContext(), R.color.purple_500)
            }

        val rv = view.findViewById<RecyclerView>(androidx.preference.R.id.recycler_view)
        rv?.viewTreeObserver?.addOnDrawListener {
            rv.children.forEach { pref ->
                val icon = pref.findViewById<View>(android.R.id.icon) as? PreferenceImageView
                icon?.let {
                    if (it.tag != "painted") {
                        it.setColorFilter(
                            color,
                            PorterDuff.Mode.SRC_IN
                        )
                        it.tag = "painted"
                    }
                }
            }
        }
    }
}