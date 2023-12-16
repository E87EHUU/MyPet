package com.example.mypet.domain.alarm

data class AlarmSwitchModel(
    val alarmId: Int,
    val alarmHour: Int,
    val alarmMinute: Int,
    val alertIsActive: Boolean
)