package com.example.mypet.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val settings: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        darkModeSettings()
    }

    private fun darkModeSettings() {
        with (binding) {
            settingsDarkMode.lineImage.setImageResource(R.drawable.icon_dark_mode)
            settingsDarkMode.lineText.text = getString(R.string.settings_dark_mode)

            if (settings.getDarkMode() == null) {
                // switch position is determined by system mode
                settingsDarkMode.lineSwitch.isChecked =
                    requireContext().resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) ==
                            Configuration.UI_MODE_NIGHT_YES
            } else {
                // switch position is determined by manual mode
                settingsDarkMode.lineSwitch.isChecked =
                    AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            }

            // switching by tapping the whole settings line
            settingsDarkMode.lineBlock.setOnClickListener {
                settingsDarkMode.lineSwitch.isChecked = !binding.settingsDarkMode.lineSwitch.isChecked
            }

            // manual dark mode is determined by switch position
            settingsDarkMode.lineSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    settings.saveDarkMode(true)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    settings.saveDarkMode(false)
                }
            }
        }
    }
}