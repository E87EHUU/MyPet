package com.example.mypet.ui.preferences

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.app.R
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.DynamicColorsOptions
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

const val THEME_SYSTEM = "theme_system"
const val THEME_LIGHT = "theme_light"
const val THEME_DARK = "theme_dark"
const val COLOR_DEFAULT = "color_default"
const val COLOR_DYNAMIC = "color_dynamic"

class PreferencesViewModel(application: Application) : AndroidViewModel(application) {

    private val userPreferences = UserPreferences(application)
    private var sharedPref: SharedPreferences? = null

    var appIsRunning = false

    val getThemeFlow = userPreferences.themeFlow

    fun saveThemeToDataStore(theme: String) {
        viewModelScope.launch(IO) {
            userPreferences.saveThemeToDataStore(theme)
        }
    }

    suspend fun applyThemeMode() {
        when (userPreferences.themeFlow.first()) {
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun saveColor(color: String) {
        sharedPref?.edit()?.putString(COLOR_KEY, color)?.apply()
    }

    fun loadColor(activity: Activity) {
        when (loadColorValue(activity.baseContext)) {
            COLOR_DYNAMIC -> DynamicColors.applyToActivityIfAvailable(activity)
            COLOR_DEFAULT -> DynamicColors.applyToActivityIfAvailable(
                activity,
                DynamicColorsOptions
                    .Builder()
                    .setThemeOverlay(R.style.Base_Theme_MyPet)
                    .build()
            )
        }
    }

    fun loadColorValue(context: Context): String {
        sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        return sharedPref?.getString(COLOR_KEY, COLOR_DEFAULT) ?: COLOR_DEFAULT
    }

    companion object {
        const val PREFERENCES = "preferences"
        const val COLOR_KEY = "color_key"
    }
}