package com.example.mypet.domain.food.detail

import android.net.Uri

data class FoodDetailModel(
    val foodId: Int,
    val foodTitle: String,
    val foodRation: String?,

    val myAvatarUri: Uri?,

    val kindOrdinal: Int,
    val breedOrdinal: Int?,

    val alarmId: Int?,
    val alarmHour: Int?,
    val alarmMinute: Int?,
    val alarmIsRepeatMonday: Boolean?,
    val alarmIsRepeatTuesday: Boolean?,
    val alarmIsRepeatWednesday: Boolean?,
    val alarmIsRepeatThursday: Boolean?,
    val alarmIsRepeatFriday: Boolean?,
    val alarmIsRepeatSaturday: Boolean?,
    val alarmIsRepeatSunday: Boolean?,
    val alarmRingtonePath: String?,
    val alarmIsVibration: Boolean?,
    val alarmIsDelay: Boolean?,
    val alarmIsActive: Boolean?,

    val delayMinute: Int = 1,
)