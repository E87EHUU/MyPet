package com.example.mypet.domain.alarm.service

data class AlarmServiceModel(
    val petId: Int,
    val petAvatarPath: String?,
    val petKindOrdinal: Int,
    val petBreedOrdinal: Int?,

    val alarmId: Int,
    val alarmDescription: String?,
    val alarmHour: Int,
    val alarmMinute: Int,

    val alarmRingtonePath: String?,
    val alarmIsVibration: Boolean,
    val alarmIsDelay: Boolean,
    val alarmIsActive: Boolean,
)