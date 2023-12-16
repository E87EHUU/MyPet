package com.example.mypet.domain.alarm.receiver

data class AlarmReceiverModel(
    val petId: Int,
    val petName: String,
    val petAvatarPath: String?,
    val petKindOrdinal: Int,
    val petBreedOrdinal: Int?,

    val careTypeOrdinal: Int,

    val alarmId: Int,
    val alarmDescription: String?,
)