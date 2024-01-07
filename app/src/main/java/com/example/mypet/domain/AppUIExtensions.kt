package com.example.mypet.domain

import android.content.Context
import com.example.mypet.app.R
import java.text.DecimalFormat
import java.util.Calendar

private val dateTimeFormatter = DecimalFormat("00")
private fun Int.formatDateTime(): String = dateTimeFormatter.format(this)

fun toAppTime(hour: Int, minute: Int) =
    "${hour.formatDateTime()}:${minute.formatDateTime()}"

fun toAppDate(timeInMillis: Long?): String {
    val calendar = Calendar.getInstance()
    timeInMillis?.let { calendar.timeInMillis = it }
    val rDay = calendar[Calendar.DAY_OF_MONTH]
    val rMonth = calendar[Calendar.MONTH] + 1
    val rYear = calendar[Calendar.YEAR]

    return "${rDay.formatDateTime()}.${rMonth.formatDateTime()}.${rYear}"
}

fun toAppDate(day: Int, month: Int) =
    "$day.$month"

fun toAppNextDateTime(timeInMillis: Long, context: Context? = null): String {
    val calendarNow = Calendar.getInstance()
    val calendarNext = Calendar.getInstance()
    calendarNext.timeInMillis = timeInMillis

    return if (calendarNow[Calendar.DAY_OF_MONTH] == calendarNext[Calendar.DAY_OF_MONTH])
        toAppTime(calendarNext[Calendar.HOUR_OF_DAY], calendarNext[Calendar.MINUTE])
    else if (calendarNow[Calendar.DAY_OF_MONTH] + 1 == calendarNext[Calendar.DAY_OF_MONTH])
        context?.getString(R.string.tomorrow)
            ?: toAppDate(calendarNext[Calendar.DAY_OF_MONTH], calendarNext[Calendar.MONTH])
    else if (calendarNow[Calendar.YEAR] == calendarNext[Calendar.YEAR])
        toAppDate(calendarNext[Calendar.DAY_OF_MONTH], calendarNext[Calendar.MONTH])
    else
        toAppDate(timeInMillis)
}