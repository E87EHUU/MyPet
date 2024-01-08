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
        val calendarNext = Calendar.getInstance()
        val nowCalendar = Calendar.getInstance()
        val calendarBefore = Calendar.getInstance()

        if (isNotStart(nowCalendar, localStartEntity))
            localStartEntity?.let { calendarNext.timeInMillis = it.timeInMillis }

        calendarNext[Calendar.HOUR_OF_DAY] = localAlarmEntity.hour
        calendarNext[Calendar.MINUTE] = localAlarmEntity.minute
        calendarNext[Calendar.SECOND] = 0
        calendarNext[Calendar.MILLISECOND] = 0

        localRepeatEntity?.let {
            calendarNext.updateNext(
                localAlarmEntity,
                nowCalendar
            )

            localEndEntity?.let {
                if (isEnd(nowCalendar, localEndEntity))
                    return localAlarmEntity.copy(intervalStart = null, nextStart = null)
            }

            calendarBefore.timeInMillis = calendarNext.timeInMillis
            calendarBefore.updateBefore(localRepeatEntity)
        }

        println("AlarmCalculator calculate()")
        println("Before alarm ${calendarBefore.time}")
        println("Next alarm ${calendarNext.time}")

        return localAlarmEntity.copy(
            intervalStart = calendarNext.timeInMillis - calendarBefore.timeInMillis,
            nextStart = calendarNext.timeInMillis
        )
    }

    private fun isNotStart(nowCalendar: Calendar, localStartEntity: LocalStartEntity?) =
        localStartEntity?.timeInMillis?.let {
            nowCalendar.timeInMillis < localStartEntity.timeInMillis
        } ?: false

    private fun isEnd(nowCalendar: Calendar, localEndEntity: LocalEndEntity) =
        when (localEndEntity.typeOrdinal) {
            CareEndTypes.NONE.ordinal -> false
            CareEndTypes.AFTER_TIMES.ordinal -> {
                localEndEntity.afterTimes?.let {
                    it < 1
                } ?: true
            }

            CareEndTypes.AFTER_TIME_IN_MILLIS.ordinal -> {
                localEndEntity.afterDate?.let {
                    nowCalendar.timeInMillis >= it
                } ?: false
            }

            else -> false
        }

    private fun Calendar.updateNext(
        localAlarmEntity: LocalAlarmEntity,
        nowCalendar: Calendar
    ) {
        localRepeatEntity?.let {
            val amount = localRepeatEntity.intervalTimes ?: 1

            when (localRepeatEntity.intervalOrdinal) {
                CareRepeatInterval.DAY.ordinal ->
                    updateWithIntervalDay(localAlarmEntity, amount, nowCalendar)

                CareRepeatInterval.WEEK.ordinal ->
                    updateWithIntervalWeek(localRepeatEntity, amount, nowCalendar)

                CareRepeatInterval.MONTH.ordinal ->
                    updateWithIntervalMonth(localAlarmEntity, amount, nowCalendar)

                CareRepeatInterval.YEAR.ordinal ->
                    updateWithIntervalYear(localAlarmEntity, amount, nowCalendar)
            }
        }
    }

    private fun Calendar.updateBefore(
        localRepeatEntity: LocalRepeatEntity
    ) {
        val amount = (localRepeatEntity.intervalTimes ?: 1) * -1

        when (localRepeatEntity.intervalOrdinal) {
            CareRepeatInterval.DAY.ordinal -> add(Calendar.DAY_OF_MONTH, amount)
            CareRepeatInterval.WEEK.ordinal -> {
                add(Calendar.WEEK_OF_YEAR, amount)

                for (i in 2..8) {
                    if (hasTodayRepeat(localRepeatEntity)) break
                    else add(Calendar.DATE, 1)
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

    private fun Calendar.equalsWeek(calendar: Calendar) =
        this[Calendar.WEEK_OF_YEAR] == calendar[Calendar.WEEK_OF_YEAR]

    private fun Calendar.updateWithIntervalDay(
        localAlarmEntity: LocalAlarmEntity,
        amount: Int,
        nowCalendar: Calendar,
    ) {
        localAlarmEntity.nextStart?.let {
            add(Calendar.DAY_OF_MONTH, amount)
        } ?: run {
            if (timeInMillis <= nowCalendar.timeInMillis)
                add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    private fun Calendar.updateWithIntervalWeek(
        localRepeatEntity: LocalRepeatEntity,
        amount: Int,
        nowCalendar: Calendar,
    ) {
        if (timeInMillis <= nowCalendar.timeInMillis) add(Calendar.DAY_OF_MONTH, 1)

        if (equalsWeek(nowCalendar)) {
            val start =
                if (this[Calendar.DAY_OF_WEEK] == 1) 8
                else this[Calendar.DAY_OF_WEEK]

            for (i in start..8) {
                if (hasTodayRepeat(localRepeatEntity)) return
                else add(Calendar.DATE, 1)
            }

            add(Calendar.WEEK_OF_YEAR, amount - 1)
        }

        for (i in 2..8) {
            if (hasTodayRepeat(localRepeatEntity)) break
            else add(Calendar.DATE, 1)
        }
    }

    private fun Calendar.updateWithIntervalMonth(
        localAlarmEntity: LocalAlarmEntity,
        amount: Int,
        nowCalendar: Calendar,
    ) {
        localAlarmEntity.nextStart?.let {
            add(Calendar.MONTH, amount)
        } ?: run {
            if (timeInMillis <= nowCalendar.timeInMillis)
                add(Calendar.MONTH, amount)
        }
    }

    private fun Calendar.updateWithIntervalYear(
        localAlarmEntity: LocalAlarmEntity,
        amount: Int,
        nowCalendar: Calendar,
    ) {
        localAlarmEntity.nextStart?.let {
            add(Calendar.YEAR, amount)
        } ?: run {
            if (timeInMillis <= nowCalendar.timeInMillis)
                add(Calendar.YEAR, amount)
        }
    }
}