package com.example.mypet.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.ActivityMainBinding
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

/*        val service = Intent(this, FoodDetailAlarmOverlayService::class.java)
        service.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        stopService(service)
        startService(service)*/
        //ContextCompat.startForegroundService(this, intent)
        requestPermissionForOverlay()
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