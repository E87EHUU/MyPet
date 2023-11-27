package com.example.mypet.ui.preferences

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPreferencesBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PreferencesFragment : Fragment(R.layout.fragment_preferences) {

    private val binding by viewBinding(FragmentPreferencesBinding::bind)
    private val preferences: PreferencesViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themePreferences()
    }

    private fun themePreferences() {
        with (binding) {
            preferencesTheme.lineImage.setImageResource(R.drawable.icon_dark_mode)
            preferencesTheme.lineText.text = getString(R.string.settings_theme)

            chipSystemTheme.onClickReaction(THEME_SYSTEM, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            chipLightTheme.onClickReaction(THEME_LIGHT, AppCompatDelegate.MODE_NIGHT_NO)
            chipDarkTheme.onClickReaction(THEME_DARK, AppCompatDelegate.MODE_NIGHT_YES)

            // switch position is determined by user preferences
            lifecycleScope.launch {
                when (preferences.getThemeFlow.first()) {
                    THEME_SYSTEM -> chipSystemTheme.isChecked = true
                    THEME_LIGHT -> chipLightTheme.isChecked = true
                    THEME_DARK -> chipDarkTheme.isChecked = true
                }
            }
        }
    }

    // Sets the selected theme mode and saves the selection
    private fun Chip.onClickReaction(theme: String, mode: Int) {
        this.setOnClickListener {
            preferences.saveToDataStore(theme)
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }
}