package com.example.mypet.ui.food.alarm

import android.content.Context
import android.text.format.DateFormat

val Context.is24HourFormat
    get() = DateFormat.is24HourFormat(this)