package com.example.mypet.ui.pet.detail.food

import java.text.DecimalFormat

private val dateTimeFormatter = DecimalFormat("00")
private fun Int.formatDateTime(): String = dateTimeFormatter.format(this)

fun toAppTime(hour: Int?, minute: Int?) =
    "${hour?.formatDateTime() ?: "--"}:${minute?.formatDateTime() ?: "--"}"