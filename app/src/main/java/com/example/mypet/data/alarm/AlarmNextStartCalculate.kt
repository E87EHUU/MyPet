package com.example.mypet.data.alarm

import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity
import com.example.mypet.domain.care.repeat.CareRepeatEndTypes
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import java.util.Calendar

class AlarmNextStartCalculate {
    fun getNextStartTimeInMillis(
        localAlarmEntity: LocalAlarmEntity,
        localStartEntity: LocalStartEntity?,
        localRepeatEntity: LocalRepeatEntity?,
    ): LocalAlarmEntity {
        val calendar = Calendar.getInstance()
        val nowTimeInMillis = calendar.timeInMillis

        if (isNotStart(nowTimeInMillis, localStartEntity))
            return localAlarmEntity.copy(nextStart = null)

        calendar[Calendar.HOUR_OF_DAY] = localAlarmEntity.hour
        calendar[Calendar.MINUTE] = localAlarmEntity.minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        if (calendar.timeInMillis <= nowTimeInMillis)
            calendar.add(Calendar.DAY_OF_MONTH, 1)

        localRepeatEntity?.let {
            if (localRepeatEntity.counter > 0) {
                calendar.calculateAndUpdateRepeatTimeInMillis(localRepeatEntity)

                if (isEnd(nowTimeInMillis, localRepeatEntity))
                    return localAlarmEntity.copy(nextStart = null)
            }
        }

        println(calendar.time)

        return localAlarmEntity.copy(nextStart = calendar.timeInMillis)
    }

    private fun isNotStart(nowTimeInMillis: Long, localStartEntity: LocalStartEntity?) =
        localStartEntity?.timeInMillis?.let {
            nowTimeInMillis < localStartEntity.timeInMillis
        } ?: false

    private fun isEnd(nowTimeInMillis: Long, localRepeatEntity: LocalRepeatEntity) =
        when (localRepeatEntity.endTypeOrdinal) {
            CareRepeatEndTypes.NONE.ordinal -> false
            CareRepeatEndTypes.AFTER_TIMES.ordinal -> {
                localRepeatEntity.endAfterTimes?.let {
                    localRepeatEntity.counter >= it
                } ?: false
            }

            CareRepeatEndTypes.AFTER_TIME_IN_MILLIS.ordinal -> {
                localRepeatEntity.endAfterTimeInMillis?.let {
                    nowTimeInMillis >= it
                } ?: false
            }

            else -> false
        }

    private fun Calendar.calculateAndUpdateRepeatTimeInMillis(
        localRepeatEntity: LocalRepeatEntity
    ) {
        val amount = localRepeatEntity.intervalTimes ?: 1
        when (localRepeatEntity.intervalOrdinal) {
            CareRepeatInterval.DAY.ordinal -> add(Calendar.DAY_OF_MONTH, amount)
            CareRepeatInterval.WEEK.ordinal -> {
                val start =
                    if (this[Calendar.DAY_OF_WEEK] == 1) 8
                    else this[Calendar.DAY_OF_WEEK]

                for (i in start..8) {
                    if (hasTodayRepeat(localRepeatEntity)) break
                    else add(Calendar.DATE, 1)
                }

                if (this[Calendar.DAY_OF_WEEK] == 2) {
                    add(Calendar.WEEK_OF_YEAR, amount)

                    for (i in 2..8) {
                        if (hasTodayRepeat(localRepeatEntity)) break
                        else add(Calendar.DATE, 1)
                    }
                }
            }

            CareRepeatInterval.MONTH.ordinal -> add(Calendar.MONTH, amount)
            CareRepeatInterval.YEAR.ordinal -> add(Calendar.YEAR, amount)
        }
    }

    private fun Calendar.hasTodayRepeat(localRepeatEntity: LocalRepeatEntity) =
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