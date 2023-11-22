package com.example.mypet.domain.food

import com.example.mypet.domain.alarm.AlarmSwitchModel

data class FoodModel(
    val foodId: Int,
    val foodTitle: String,
    val alarmId: Int?,
    val alarmHour: Int?,
    val alarmMinute: Int?,
    val alarmIsActive: Boolean?
) {
    fun toAlarmSwitchModel(): AlarmSwitchModel? {
        alarmId ?: return null
        alarmIsActive ?: return null
        alarmHour ?: return null
        alarmMinute ?: return null

        return AlarmSwitchModel(
            foodId = foodId,
            careId = null,
            alarmId = alarmId,
            alarmHour = alarmHour,
            alarmMinute = alarmMinute,
            alertIsActive = !alarmIsActive
        )
    }
}