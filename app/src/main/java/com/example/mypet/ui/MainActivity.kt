package com.example.mypet.ui

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.ActivityMainBinding
import com.example.mypet.ui.food.detail.alarm.FoodDetailAlarmOverlayService
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

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

    private lateinit var chanelId: String
    private var notificationId by Delegates.notNull<Int>()
    private lateinit var title: String
    private lateinit var description: String
    private var icon by Delegates.notNull<Int>()
    private lateinit var melody: Uri
    private var isVibration by Delegates.notNull<Boolean>()
    private var isDelay by Delegates.notNull<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(
            this@MainActivity, navController, mAppBarConfiguration
        )

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        )

/*            chanelId = this.packageName
        notificationId = intent.getIntExtra(FoodDetailAlarmOverlayService.ALARM_ID, 1)

        val intent = Intent(this, FoodDetailAlarmOverlayService::class.java)
        intent.action = FoodDetailAlarmOverlayService.ALARM_OVERLAY_ACTION_START
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val bundle = bundleOf(
            FoodDetailAlarmOverlayService.ALARM_ID to 1,
        )
        intent.putExtras(bundle)
        ContextCompat.startForegroundService(this, intent)*/
        //getForegroundNotification(intent)

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


    private fun getForegroundNotification(intent: Intent): Notification? {
        createNotificationChannel()

        val intentToService = Intent(this, FoodDetailAlarmOverlayService::class.java)

        val pendingIntentStartServiceNavToDetail =
            let {
                intentToService.action =
                    FoodDetailAlarmOverlayService.ALARM_OVERLAY_ACTION_NAV_TO_DETAIL
                PendingIntent.getService(
                    this,
                    0,
                    intentToService,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }

        val pendingIntentStartServiceDelay =
            let {
                intentToService.action = FoodDetailAlarmOverlayService.ALARM_OVERLAY_ACTION_DELAY
                PendingIntent.getService(
                    this,
                    0,
                    intentToService,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }

        val pendingIntentStartServiceStop =
            let {
                intentToService.action = FoodDetailAlarmOverlayService.ALARM_OVERLAY_ACTION_STOP
                PendingIntent.getService(
                    this,
                    0,
                    intentToService,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }

        val notification = NotificationCompat.Builder(this, "1")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(icon)
            .setAutoCancel(true)
            .setOngoing(true)
            .setContentIntent(pendingIntentStartServiceNavToDetail)
            .apply {
                if (isDelay)
                    addAction(
                        R.drawable.baseline_repeat_24,
                        getString(R.string.notification_chanel_action_delay),
                        pendingIntentStartServiceDelay
                    )
            }
            .addAction(
                R.drawable.baseline_close_24,
                getString(R.string.notification_chanel_action_stop),
                pendingIntentStartServiceStop
            )
            .build()

//        notification.flags = NotificationCompat.FLAG_FOREGROUND_SERVICE

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)

        return notification
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel("1", "title", NotificationManager.IMPORTANCE_DEFAULT)
            .apply {
                enableVibration(isVibration)
                setSound(melody, audioAttributes)
                lightColor = Color.BLUE
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}