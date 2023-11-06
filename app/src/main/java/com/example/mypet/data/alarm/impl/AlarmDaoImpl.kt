package com.example.mypet.data.alarm.impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.model.AlarmModel
import com.example.mypet.ui.MainActivity
import com.example.mypet.ui.food.detail.alarm.FoodDetailAlarmOverlayService
import com.example.mypet.ui.food.detail.alarm.FoodDetailAlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

class AlarmDaoImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AlarmDao {
    override suspend fun setAlarm(alarmModel: AlarmModel) {
        val pendingIntentStartMainActivity =
            let {
                val intent = Intent(context, MainActivity::class.java)
                PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        val pendingIntentStartAlarmReceiver =
            let {
                val intent = Intent(context, FoodDetailAlarmOverlayService::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val bundle = bundleOf(
                    FoodDetailAlarmReceiver.NOTIFICATION_ID to alarmModel.id,
                    FoodDetailAlarmReceiver.TITLE to alarmModel.title,
                    FoodDetailAlarmReceiver.MELODY to alarmModel.melody,
                    FoodDetailAlarmReceiver.DESCRIPTION to alarmModel.description,
                    FoodDetailAlarmReceiver.IS_VIBRATION to alarmModel.isVibration,
                    FoodDetailAlarmReceiver.IS_DELAY to alarmModel.isDelay,
                )
                intent.putExtras(bundle)
                PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }

        val timeInMillis =
            let {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, alarmModel.hour)
                calendar.set(Calendar.MINUTE, alarmModel.minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.timeInMillis
            }

        val clockInfo = AlarmManager.AlarmClockInfo(timeInMillis, pendingIntentStartMainActivity)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setAlarmClock(clockInfo, pendingIntentStartAlarmReceiver)
    }
}