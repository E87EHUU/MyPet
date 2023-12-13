package com.example.mypet.domain

import java.text.DecimalFormat
import java.util.Calendar

private val dateTimeFormatter = DecimalFormat("00")
private fun Int.formatDateTime(): String = dateTimeFormatter.format(this)

fun toAppTime(hour: Int, minute: Int) =
    "${hour.formatDateTime()}:${minute.formatDateTime()}"

fun toAppDate(timeInMillis: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis
    val rDay = calendar[Calendar.DAY_OF_MONTH]
    val rMonth = calendar[Calendar.MONTH] + 1
    val rYear = calendar[Calendar.YEAR]

    return "${rDay.formatDateTime()}.${rMonth.formatDateTime()}.${rYear}"
}