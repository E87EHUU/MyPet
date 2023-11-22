package com.example.mypet.ui.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private var isDarkMode: Boolean? = null
    private var darkModePreferences: SharedPreferences? = null

    fun getDarkMode() = isDarkMode

    fun saveDarkMode(isDark: Boolean) {
        isDarkMode = isDark
        darkModePreferences?.edit()?.putBoolean(DARK_MODE_KEY, isDark)?.apply()
    }

    fun loadDarkMode(context: Context?) {
        darkModePreferences = context?.getSharedPreferences(SAVE_DARK_MODE, Context.MODE_PRIVATE)
        isDarkMode = darkModePreferences?.getBoolean(DARK_MODE_KEY, false)
    }

    companion object {
        private const val SAVE_DARK_MODE = "save dark mode"
        private const val DARK_MODE_KEY = "dark mode key"
    }
}