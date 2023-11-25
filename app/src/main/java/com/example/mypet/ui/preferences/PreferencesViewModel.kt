package com.example.mypet.ui.preferences

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

const val THEME_SYSTEM = "theme_system"
const val THEME_LIGHT = "theme_light"
const val THEME_DARK = "theme_dark"

class PreferencesViewModel(application: Application) : AndroidViewModel(application) {

    private val userPreferences = UserPreferences(application)

    var appIsRunning = false

    val getThemeFlow = userPreferences.themeFlow

    fun saveToDataStore(theme: String) {
        viewModelScope.launch(IO) {
            userPreferences.saveToDataStore(theme)
        }
    }

    suspend fun applyThemeMode() {
        when (userPreferences.themeFlow.first()) {
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}