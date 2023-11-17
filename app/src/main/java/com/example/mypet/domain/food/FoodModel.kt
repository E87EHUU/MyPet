package com.example.mypet.domain.food

data class FoodModel(
    val foodId: Int,
    val foodTitle: String,
    val alarmId: Int?,
    val alarmHour: Int?,
    val alarmMinute: Int?,
    val alarmIsActive: Boolean?
)