package com.example.mypet.data.alarm

import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity
import java.util.Calendar

class AlarmNextStartCalculate {
    fun getNextStartTimeInMillis(
        localStartEntity: LocalStartEntity?,
        localRepeatEntity: LocalRepeatEntity?,
        hour: Int,
        minute: Int,
    ): Long {
        val calendar = Calendar.getInstance()

        localStartEntity?.timeInMillis?.let {
            calendar.timeInMillis = it
        }

        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        localRepeatEntity?.let {
/*            val calendarNow = Calendar.getInstance()

            if (calendar.isHourEqualsAndMinuteLast(calendarNow)
                || calendar.isHourLast(calendarNow)
            ) calendar.add(Calendar.DATE, 1)

            if (alarmModel.isRepeatable()) {
                for (i in 1..7) {
                    if (calendar.hasTodayAlarm(alarmModel) != null) break
                    else calendar.add(Calendar.DATE, 1)
                }
            }*/
        }
    println(calendar.time)
        return calendar.timeInMillis
    }

    private fun Calendar.hasTodayAlarm(localRepeatEntity: LocalRepeatEntity) =
        when (this[Calendar.DAY_OF_WEEK]) {
            2 -> localRepeatEntity.isMonday
            3 -> localRepeatEntity.isTuesday
            4 -> localRepeatEntity.isWednesday
            5 -> localRepeatEntity.isThursday
            6 -> localRepeatEntity.isFriday
            7 -> localRepeatEntity.isSaturday
            1 -> localRepeatEntity.isSunday
            else -> false
        }

    private fun Calendar.isHourEqualsAndMinuteLast(nowCalendar: Calendar) =
        nowCalendar[Calendar.HOUR] == this[Calendar.HOUR]
                && nowCalendar[Calendar.MINUTE] > this[Calendar.MINUTE]

    private fun Calendar.isHourLast(nowCalendar: Calendar) =
        nowCalendar[Calendar.HOUR] > this[Calendar.HOUR]

}