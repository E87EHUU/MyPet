package com.example.mypet.domain.alarm.service

data class AlarmServiceModel(
    val petId: Int,
    val petName: String,
    val petAvatarPath: String?,
    val petKindOrdinal: Int,
    val petBreedOrdinal: Int?,

    val careTypeOrdinal: Int,
    val careTitle: String?,
    val careDose: String?,

    val alarmId: Int,
    val alarmHour: Int,
    val alarmMinute: Int,

    val alarmRingtonePath: String?,
    val alarmIsVibration: Boolean,
    val alarmIsDelay: Boolean,
    val alarmIsActive: Boolean,

    val isOverlayEnable: Boolean = false,
)