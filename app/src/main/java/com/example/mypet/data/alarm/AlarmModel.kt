package com.example.mypet.data.alarm

data class AlarmModel(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val isRepeatMonday: Boolean = false,
    val isRepeatTuesday: Boolean = false,
    val isRepeatWednesday: Boolean = false,
    val isRepeatThursday: Boolean = false,
    val isRepeatFriday: Boolean = false,
    val isRepeatSaturday: Boolean = false,
    val isRepeatSunday: Boolean = false,
    val delayMinute: Int? = null
)