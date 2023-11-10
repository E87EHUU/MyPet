package com.example.mypet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val topLevelDestinations = setOf(
        R.id.petDetail,
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

        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(
            this@MainActivity, navController, mAppBarConfiguration
        )

        setNavigationBarView()
    }

    private fun setNavigationBarView() {
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.vet_map -> {
                    // Respond to navigation item 1 click
                    true
                }

                R.id.reminder -> {
                    // Respond to navigation item 2 click
                    true
                }

                R.id.home -> {
                    // Respond to navigation item 3 click
                    true
                }

                R.id.food -> {
                    // Respond to navigation item 4 click
                    true
                }

                R.id.profile -> {
                    // Respond to navigation item 5 click
                    true
                }

                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.home
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.navHostMain)
        return (navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp())
    }
}