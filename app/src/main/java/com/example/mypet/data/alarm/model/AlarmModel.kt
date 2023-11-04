package com.example.mypet.data.alarm.model

data class AlarmModel(
    val id: Int,
    val title: String,
    val description: String?,
    val hour: Int,
    val minute: Int,
    val melody: String?,
    val isVibration: Boolean,
    val isDelay: Boolean,
)