package com.example.mypet.ui.preferences

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPreferencesBinding
import com.google.android.material.chip.Chip

class PreferencesFragment : Fragment(R.layout.fragment_preferences) {

    private val binding by viewBinding(FragmentPreferencesBinding::bind)
    private val preferences: PreferencesViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themePreferences()
        colorPreferences()
    }

    private fun themePreferences() {
        with (binding) {
            preferencesTheme.lineImage.setImageResource(R.drawable.icon_dark_mode)
            preferencesTheme.lineText.text = getString(R.string.preferences_theme)

            chipSystemTheme.onThemeClickReaction(THEME_SYSTEM)
            chipLightTheme.onThemeClickReaction(THEME_LIGHT)
            chipDarkTheme.onThemeClickReaction(THEME_DARK)

            when (preferences.loadThemeValue(requireContext())) {
                THEME_SYSTEM -> chipSystemTheme.isChecked = true
                THEME_LIGHT -> chipLightTheme.isChecked = true
                THEME_DARK -> chipDarkTheme.isChecked = true
            }
        }
    }

    private fun Chip.onThemeClickReaction(theme: String) {
        this.setOnClickListener {
            preferences.run {
                saveTheme(theme)
                loadTheme(requireActivity())
            }
        }
    }

    private fun colorPreferences() {
        with (binding) {
            preferencesColor.lineImage.setImageResource(R.drawable.icon_color_scheme)
            preferencesColor.lineText.text = getString(R.string.preferences_color)

            chipDynamicColor.onColorClickReaction(COLOR_DYNAMIC)
            chipDefaultColor.onColorClickReaction(COLOR_DEFAULT)

            when (preferences.loadColorValue(requireContext())) {
                COLOR_DYNAMIC -> chipDynamicColor.isChecked = true
                COLOR_DEFAULT -> chipDefaultColor.isChecked = true
            }
        }
    }

    private fun Chip.onColorClickReaction(color: String) {
        this.setOnClickListener {
            preferences.run {
                saveColor(color)
                loadColor(requireActivity())
            }
            refreshCurrentFragment()
        }
    }

    private fun refreshCurrentFragment() {
        val id = findNavController().currentDestination?.id
        findNavController().run {
            popBackStack(id!!, true)
            navigate(id)
        }
    }
}