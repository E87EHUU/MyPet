package com.example.mypet.ui.alarm.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.mypet.app.R
import com.example.mypet.domain.alarm.service.AlarmServiceModel


class AlarmServiceNotification(
    private val context: Context,
    private val alarmServiceModel: AlarmServiceModel,
) {
    private val intentToService = Intent(context, AlarmService::class.java)
    private val pendingIntentStartServiceNavToDetail: PendingIntent =
        let {
            intentToService.action =
                AlarmService.ALARM_OVERLAY_ACTION_NAV_TO_DETAIL
            PendingIntent.getService(
                context,
                0,
                intentToService,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

    private val pendingIntentStartServiceDelay: PendingIntent =
        let {
            intentToService.action = AlarmService.ALARM_OVERLAY_ACTION_DELAY
            PendingIntent.getService(
                context,
                0,
                intentToService,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

    private val pendingIntentStartServiceStop: PendingIntent =
        let {
            intentToService.action = AlarmService.ALARM_OVERLAY_ACTION_STOP
            PendingIntent.getService(
                context,
                0,
                intentToService,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

    init {
        createNotificationChannel()
    }

    fun getDelayNotification() =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            //.setContentTitle(alarmModel.anyTitle)
            .setContentIntent(pendingIntentStartServiceNavToDetail)
            .setContentText(context.getString(R.string.alarm_delay_message))
            .addAction(
                R.drawable.baseline_close_24,
                context.getString(R.string.alarm_notification_chanel_action_stop),
                pendingIntentStartServiceStop
            )
            .build()

    fun getNotification() =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            // .setContentTitle(alarmModel.anyTitle)
            .setContentText("")
            .setContentIntent(pendingIntentStartServiceNavToDetail)
            .apply {
                if (alarmServiceModel.alarmIsDelay)
                    addAction(
                        R.drawable.baseline_repeat_24,
                        context.getString(R.string.alarm_notification_chanel_action_delay),
                        pendingIntentStartServiceDelay
                    )
            }
            .addAction(
                R.drawable.baseline_close_24,
                context.getString(R.string.alarm_notification_chanel_action_stop),
                pendingIntentStartServiceStop
            )
            .build()

    private fun createNotificationChannel() {
        val importance =
            if (alarmServiceModel.isOverlayEnable) NotificationManager.IMPORTANCE_DEFAULT
            else NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.app_name),
            importance
        )
            .apply {
                lightColor = Color.BLUE
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "my_pet_notification"
    }
}