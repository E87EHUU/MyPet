package com.example.mypet.data.alarm

import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalEndEntity
import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity
import com.example.mypet.domain.care.end.CareEndTypes
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import java.util.Calendar

class AlarmCalculator(
    private val localStartEntity: LocalStartEntity?,
    private val localRepeatEntity: LocalRepeatEntity?,
    private val localEndEntity: LocalEndEntity?,
) {
    fun calculate(
        localAlarmEntity: LocalAlarmEntity,
    ): LocalAlarmEntity {
        val calendar = Calendar.getInstance()
        val nowTimeInMillis = calendar.timeInMillis

        if (isNotStart(nowTimeInMillis, localStartEntity))
            return localAlarmEntity.copy(beforeStart = null, nextStart = null)

        calendar[Calendar.HOUR_OF_DAY] = localAlarmEntity.hour
        calendar[Calendar.MINUTE] = localAlarmEntity.minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        if (calendar.timeInMillis <= nowTimeInMillis)
            calendar.add(Calendar.DAY_OF_MONTH, 1)

        localRepeatEntity?.let {
            localEndEntity?.let {
                if (localEndEntity.counter > 0) {
                    calendar.calculateAndUpdateRepeatTimeInMillis(localRepeatEntity)

                    if (isEnd(nowTimeInMillis, localEndEntity))
                        return localAlarmEntity.copy(beforeStart = null, nextStart = null)
                }
            }
        }

        println("Next alarm ${calendar.time}")
        println("before ${calendar.timeInMillis - nowTimeInMillis}")
        println("next ${calendar.timeInMillis}")

        return localAlarmEntity.copy(
            beforeStart = calendar.timeInMillis - nowTimeInMillis,
            nextStart = calendar.timeInMillis
        )
    }

    private fun isNotStart(nowTimeInMillis: Long, localStartEntity: LocalStartEntity?) =
        localStartEntity?.timeInMillis?.let {
            nowTimeInMillis < localStartEntity.timeInMillis
        } ?: false

    private fun isEnd(nowTimeInMillis: Long, localEndEntity: LocalEndEntity) =
        when (localEndEntity.typeOrdinal) {
            CareEndTypes.NONE.ordinal -> false
            CareEndTypes.AFTER_TIMES.ordinal -> {
                localEndEntity.afterTimes?.let {
                    localEndEntity.counter >= it
                } ?: false
            }

            CareEndTypes.AFTER_TIME_IN_MILLIS.ordinal -> {
                localEndEntity.afterDate?.let {
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
}