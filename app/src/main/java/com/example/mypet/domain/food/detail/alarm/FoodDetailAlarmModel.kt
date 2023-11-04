package com.example.mypet.domain.food.detail.alarm

import android.net.Uri

data class FoodDetailAlarmModel(
    val id: Int,
    val name: String,

    var alarmId: Int?,
    val alarmHour: Int?,
    val alarmMinute: Int?,
    val alarmRepeatMonday: Boolean?,
    val alarmRepeatTuesday: Boolean?,
    val alarmRepeatWednesday: Boolean?,
    val alarmRepeatThursday: Boolean?,
    val alarmRepeatFriday: Boolean?,
    val alarmRepeatSaturday: Boolean?,
    val alarmRepeatSunday: Boolean?,
    val alarmMelodyURI: Uri?,
    val alarmIsVibration: Boolean?,
    val alarmIsDelay: Boolean?,
    val alarmIsActive: Boolean?
)