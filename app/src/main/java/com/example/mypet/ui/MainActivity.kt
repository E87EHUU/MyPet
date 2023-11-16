package com.example.mypet.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val topLevelDestinations = setOf(
        R.id.map,
        R.id.pet,
        R.id.food
    )

    private val mAppBarConfiguration by lazy {
        AppBarConfiguration(topLevelDestinations)
    }

    private val navController by lazy {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
        navHost.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionForOverlay()

        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(
            this@MainActivity, navController, mAppBarConfiguration
        )

        setNavigationBarView()

        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setNavigationBarView() {
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map -> {
                    navController.navigate(R.id.map)
                    true
                }

                R.id.reminder -> {
                    // Respond to navigation item 2 click
                    true
                }

                R.id.pet -> {
                    navController.navigate(R.id.pet)
                    true
                }

                R.id.food -> {
                    navController.navigate(R.id.food)
                    true
                }

                R.id.profile -> {
                    // Respond to navigation item 5 click
                    true
                }

                else -> false
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) requestPermissionForOverlay()

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.navHostMain)
        return (navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp())
    }

    private fun requestPermissionForOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivityForResult(intent, 0)
        }
    }
}