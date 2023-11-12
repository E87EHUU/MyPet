package com.example.mypet.domain.pet.detail

data class PetFoodModel(
    val id: Int,
    val name: String,
    val alarmId: Int?,
    val alarmHour: Int?,
    val alarmMinute: Int?,
    val alarmIsActive: Boolean?
)
