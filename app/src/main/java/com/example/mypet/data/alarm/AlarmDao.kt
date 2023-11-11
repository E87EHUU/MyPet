package com.example.mypet.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mypet.ui.MainActivity
import com.example.mypet.ui.food.detail.alarm.FoodDetailAlarmOverlayService
import com.example.mypet.ui.food.detail.alarm.FoodDetailAlarmOverlayService.Companion.ALARM_ID
import com.example.mypet.ui.food.detail.alarm.FoodDetailAlarmOverlayService.Companion.ALARM_OVERLAY_ACTION_DELAY
import com.example.mypet.ui.food.detail.alarm.FoodDetailAlarmOverlayService.Companion.ALARM_OVERLAY_ACTION_START
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

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
            getStartAlarmServicePendingIntent(alarmModel.id, alarmModel.delay)
        )
    }

    override suspend fun removeAlarm(alarmId: Int) {
        alarmManager.cancel(getStartAlarmServicePendingIntent(alarmId))
    }

    private fun getTimeMillis(alarmModel: AlarmModel) =
        with(alarmModel) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            alarmModel.delay?.let { calendar.add(Calendar.MINUTE, it) }

            val calendarNow = Calendar.getInstance()

            if (calendarNow.isHourEqualsAndMinuteLast(calendarNow)
                || calendarNow.isHourLast(calendarNow)
            ) calendar.add(Calendar.DATE, 1)

            var addDay = 0
            for (i in 1..7) {
                if (calendar.hasTodayAlarm(alarmModel)) continue
                else addDay++
            }
            println(addDay)
            if (addDay in 1..6)
                calendar.add(Calendar.DATE, addDay)

            println(calendar.time)
            calendar.timeInMillis
        }

    private fun Calendar.hasTodayAlarm(alarmModel: AlarmModel) =
        when (this[Calendar.DAY_OF_WEEK]) {
            Calendar.MONDAY -> alarmModel.isRepeatMonday
            Calendar.TUESDAY -> alarmModel.isRepeatTuesday
            Calendar.WEDNESDAY -> alarmModel.isRepeatWednesday
            Calendar.THURSDAY -> alarmModel.isRepeatThursday
            Calendar.FRIDAY -> alarmModel.isRepeatFriday
            Calendar.SATURDAY -> alarmModel.isRepeatSaturday
            Calendar.SUNDAY -> alarmModel.isRepeatSunday
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

    private fun getStartAlarmServicePendingIntent(id: Int, delay: Int? = null) =
        let {
            val intent = Intent(context, FoodDetailAlarmOverlayService::class.java)
            intent.action = delay
                ?.let { ALARM_OVERLAY_ACTION_DELAY }
                ?: run { ALARM_OVERLAY_ACTION_START }
            intent.putExtra(ALARM_ID, id)
            PendingIntent.getForegroundService(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
}