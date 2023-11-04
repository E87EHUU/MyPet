package com.example.mypet.ui.food.detail.alarm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.mypet.app.R
import com.example.mypet.ui.MainActivity


class FoodDetailAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        if (intent == null) return

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) return

        val pendingIntentStartMainActivity =
            let {
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }

        val pendingIntentStartServiceToDelayAlert = pendingIntentStartMainActivity
        val pendingIntentStartServiceToStopAlert = pendingIntentStartMainActivity

        val chanelId = context.packageName
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        val title = intent.getStringExtra(TITLE)
        val description = intent.getStringExtra(DESCRIPTION)
        val icon = intent.getIntExtra(ICON, R.mipmap.ic_launcher)
        val isDelay = intent.getBooleanExtra(IS_DELAY, false)

        val notification = NotificationCompat.Builder(context, chanelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(icon)
            .setAutoCancel(true)
            .setOngoing(true)
            .setContentIntent(pendingIntentStartMainActivity)
            .apply {
                if (isDelay)
                    addAction(
                        R.drawable.baseline_repeat_24,
                        context.getString(R.string.notification_chanel_action_delay),
                        pendingIntentStartServiceToDelayAlert
                    )
            }
            .addAction(
                R.drawable.baseline_close_24,
                context.getString(R.string.notification_chanel_action_stop),
                pendingIntentStartServiceToStopAlert
            )
            .build()

        notification.flags = NotificationCompat.FLAG_BUBBLE

        createNotificationChannel(context, chanelId, intent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    private fun createNotificationChannel(context: Context, id: String, intent: Intent) {
        val name = context.getString(R.string.app_name)
        val melody = Uri.parse(intent.getStringExtra(MELODY))
            ?: Settings.System.DEFAULT_ALARM_ALERT_URI
        val isVibration = intent.getBooleanExtra(IS_VIBRATION, false)

        val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            .apply {
                enableVibration(isVibration)
                setSound(melody, audioAttributes)
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val NOTIFICATION_ID = "channel_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val ICON = "icon"
        const val MELODY = "melody"
        const val IS_VIBRATION = "is_vibration"
        const val IS_DELAY = "is_delay"
    }
}