package com.example.mypet.ui.preferences

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import com.example.mypet.app.R
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.DynamicColorsOptions

const val THEME_SYSTEM = "theme_system"
const val THEME_LIGHT = "theme_light"
const val THEME_DARK = "theme_dark"
const val COLOR_DEFAULT = "color_default"
const val COLOR_DYNAMIC = "color_dynamic"

class PreferencesViewModel(application: Application) : AndroidViewModel(application) {

    private var sharedPref: SharedPreferences? = null

    var appIsRunning = false

    fun saveTheme(theme: String) {
        sharedPref?.edit()?.putString(THEME_KEY, theme)?.apply()
    }

    fun loadTheme(activity: Activity) {
        when (loadThemeValue(activity.baseContext)) {
            THEME_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    fun loadThemeValue(context: Context): String {
        sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        return sharedPref?.getString(THEME_KEY, THEME_SYSTEM) ?: THEME_SYSTEM
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
        const val THEME_KEY = "theme_key"
    }
}