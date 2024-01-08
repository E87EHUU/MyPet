package com.example.mypet.broadcast

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.mypet.app.R
import com.example.mypet.domain.alarm.receiver.AlarmReceiverModel
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.ui.MainActivity


class AlarmReceiverNotification(
    private val context: Context,
    private val alarmReceiverModel: AlarmReceiverModel,
) {
    private val intentToApp = Intent(context, MainActivity::class.java)
    private val pendingIntentStartActivityNavToCare: PendingIntent =
        let {
            PendingIntent.getActivity(
                context,
                0,
                intentToApp,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

    fun getNotification(): Notification {
        with(alarmReceiverModel) {
            val text = careTitle ?: careDose
            ?: context.getString(CareTypes.entries[careTypeOrdinal].titleResId)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(petName)
                .setContentText(text)
                .setContentIntent(pendingIntentStartActivityNavToCare)
                .build()

            notification.flags = notification.flags and Notification.FLAG_INSISTENT

            return notification
        }
    }

    fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "my_pet_notification"
    }
}