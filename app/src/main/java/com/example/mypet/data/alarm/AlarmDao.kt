package com.example.mypet.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mypet.ui.MainActivity
import com.example.mypet.ui.food.alarm.service.FoodAlarmService
import com.example.mypet.ui.food.alarm.service.FoodAlarmService.Companion.ALARM_ID
import com.example.mypet.ui.food.alarm.service.FoodAlarmService.Companion.ALARM_OVERLAY_ACTION_START
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

fun AlarmModel.isRepeatable() =
    isRepeatMonday || isRepeatTuesday || isRepeatWednesday
            || isRepeatThursday || isRepeatFriday || isRepeatSaturday || isRepeatSunday

class AlarmDao @Inject constructor(
    @ApplicationContext private val context: Context,
) : IAlarmDao {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override suspend fun setAlarm(alarmModel: AlarmModel) {
        val clockInfo = AlarmManager.AlarmClockInfo(
            getTimeMillis(alarmModel),
            getStartMainActivityPendingIntent(alarmModel.id)
        )

        alarmManager.setAlarmClock(
            clockInfo,
            getStartAlarmServicePendingIntent(alarmModel.id)
        )
    }

    override suspend fun removeAlarm(alarmId: Int) {
        alarmManager.cancel(getStartAlarmServicePendingIntent(alarmId))
    }

    private fun getTimeMillis(alarmModel: AlarmModel) =
        let {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            alarmModel.delayMinute?.let {
                calendar.add(Calendar.MINUTE, it)
            } ?: run {
                calendar.set(Calendar.HOUR_OF_DAY, alarmModel.hour)
                calendar.set(Calendar.MINUTE, alarmModel.minute)

                val calendarNow = Calendar.getInstance()
                if (calendarNow.isHourEqualsAndMinuteLast(calendarNow)
                    || calendarNow.isHourLast(calendarNow)
                ) calendar.add(Calendar.DATE, 1)

                if (alarmModel.isRepeatable()) {
                    for (i in 1..7) {
                        if (calendar.hasTodayAlarm(alarmModel)) break
                        else calendar.add(Calendar.DATE, 1)
                    }
                }
            }

            calendar.timeInMillis
        }

    private fun Calendar.hasTodayAlarm(alarmModel: AlarmModel) =
        when (this[Calendar.DAY_OF_WEEK]) {
            2 -> alarmModel.isRepeatMonday
            3 -> alarmModel.isRepeatTuesday
            4 -> alarmModel.isRepeatWednesday
            5 -> alarmModel.isRepeatThursday
            6 -> alarmModel.isRepeatFriday
            7 -> alarmModel.isRepeatSaturday
            1 -> alarmModel.isRepeatSunday
            else -> false
        }

    private fun Calendar.isHourEqualsAndMinuteLast(nowCalendar: Calendar) =
        nowCalendar[Calendar.HOUR] == this[Calendar.HOUR]
                && nowCalendar[Calendar.MINUTE] > this[Calendar.MINUTE]

    private fun Calendar.isHourLast(nowCalendar: Calendar) =
        nowCalendar[Calendar.HOUR] > this[Calendar.HOUR]

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
            val intent = Intent(context, FoodAlarmService::class.java)
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