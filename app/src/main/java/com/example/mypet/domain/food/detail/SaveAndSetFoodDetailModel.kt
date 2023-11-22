package com.example.mypet.domain.food.detail

data class SaveAndSetFoodDetailModel(
    val petMyId: Int,

    val foodId: Int? = null,
    val foodName: String,

    val alarmId: Int? = null,
    val alarmHour: Int,
    val alarmMinute: Int,
    val alarmRepeatMonday: Boolean,
    val alarmRepeatTuesday: Boolean,
    val alarmRepeatWednesday: Boolean,
    val alarmRepeatThursday: Boolean,
    val alarmRepeatFriday: Boolean,
    val alarmRepeatSaturday: Boolean,
    val alarmRepeatSunday: Boolean,
    val alarmRingtonePath: String?,
    val alarmIsVibration: Boolean,
    val alarmIsDelay: Boolean,
) {

}