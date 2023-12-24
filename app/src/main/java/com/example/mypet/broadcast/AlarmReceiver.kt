package com.example.mypet.broadcast

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mypet.domain.AlarmReceiverRepository
import com.example.mypet.service.alarm.AlarmService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var alarmReceiverRepository: AlarmReceiverRepository

    @ApplicationContext
    lateinit var context: Context

    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            intent.getId()?.let { id ->
                runBlocking {
                    launch(Dispatchers.IO) {
                        alarmReceiverRepository.getAlarmReceiverModel(id)
                            ?.let {
                                val notificationManager = NotificationManagerCompat.from(context)
                                val notification = AlarmReceiverNotification(context, it)
                                notification.createNotificationChannel()
                                notificationManager.notify(it.alarmId, notification.getNotification())
                            }
                    }
                }
            }

            return
        }
    }

    private fun Intent.getId(): Int? {
        val alarmId = getIntExtra(AlarmService.ALARM_ID, 0)
        return if (alarmId != 0) return alarmId else null
    }
}