package com.example.mypet.domain.pet

data class PetFoodAlarmModel(
    val alarmId: Int,
    val time: String,
    val isActive: Boolean,
)