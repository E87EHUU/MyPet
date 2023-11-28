package com.example.mypet.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.ActivityMainBinding
import com.example.mypet.ui.preferences.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val preferences: PreferencesViewModel by viewModels()

    private val topLevelDestinations = setOf(
        R.id.navigationMap,
        R.id.navigationPet,
        R.id.navigationUser
    )

    private val mAppBarConfiguration by lazy {
        AppBarConfiguration(topLevelDestinations)
    }

    private val navController by lazy {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
        navHost.navController
    }

/*    private val toolbarOnDestinations = mapOf(
        R.id.careFragment to R.menu.toolbar_pet_detail,
    )

    private val fabDestinations = setOf(
        R.id.careFragment,
    )*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyPreferences()

        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(
            this@MainActivity, navController, mAppBarConfiguration
        )

        binding.bottomNavigation.setupWithNavController(navController)
        //observeNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.navHostMain)
        return (navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp())
    }

/*    private fun observeNavigation() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                navController.currentBackStack.collectLatest {
                    val lastId = it.last().destination.id
//                    if (fabDestinations.contains(lastId))
//                        binding.floatingActionButton.hide()
//                    else
//                        binding.floatingActionButton.show()

                    toolbarOnDestinations[lastId]?.let { menuId ->
                        binding.toolbar.menu.clear()
                        binding.toolbar.inflateMenu(menuId)
                    } ?: run {
                        binding.toolbar.menu.clear()
                        binding.toolbar.inflateMenu(R.menu.toolbar_empty)
                    }
                }
            }
        }
    }*/

    private fun applyPreferences() {
        lifecycleScope.launch {
            if (!preferences.appIsRunning) {
                preferences.run {
                    appIsRunning = true
                    applyThemeMode()
                }
            }
        }
    }
}