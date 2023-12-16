package com.example.mypet.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mypet.ui.MainActivity
import com.example.mypet.ui.alarm.service.AlarmService
import com.example.mypet.ui.alarm.service.AlarmService.Companion.ALARM_ID
import com.example.mypet.ui.alarm.service.AlarmService.Companion.ALARM_OVERLAY_ACTION_START
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmDao @Inject constructor(
    @ApplicationContext private val context: Context,
) : IAlarmDao {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override suspend fun setAlarm(alarmId: Int, alarmTime: Long) {
        val clockInfo = AlarmManager.AlarmClockInfo(
            alarmTime,
            getStartMainActivityPendingIntent(alarmId)
        )

        alarmManager.setAlarmClock(
            clockInfo,
            getStartAlarmServicePendingIntent(alarmId)
        )
    }

    override suspend fun removeAlarm(alarmId: Int) {
        alarmManager.cancel(getStartAlarmServicePendingIntent(alarmId))
    }

    private fun getStartMainActivityPendingIntent(id: Int) =
        let {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            PendingIntent.getActivity(
                context,
                id,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

    private fun getStartAlarmServicePendingIntent(id: Int) =
        let {
            val intent = Intent(context, AlarmService::class.java)
            intent.action = ALARM_OVERLAY_ACTION_START
            intent.putExtra(ALARM_ID, id)
            PendingIntent.getForegroundService(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
}