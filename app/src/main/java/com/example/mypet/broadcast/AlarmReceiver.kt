package com.example.mypet.broadcast

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mypet.domain.AlarmServiceRepository
import com.example.mypet.service.alarm.AlarmService
import com.example.mypet.service.alarm.AlarmServiceNotification
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var alarmServiceRepository: AlarmServiceRepository

    @ApplicationContext
    lateinit var context: Context

    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            intent.getId()?.let { id ->
                runBlocking {
                    launch(Dispatchers.IO) {
                        alarmServiceRepository.getAlarmServiceModel(id)
                            ?.let {
                                val notificationManager = NotificationManagerCompat.from(context)
                                val notification = AlarmServiceNotification(context)
                                notification.createNotificationChannel()
                                notificationManager.notify(it.alarmId, notification.getNotification(it))
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