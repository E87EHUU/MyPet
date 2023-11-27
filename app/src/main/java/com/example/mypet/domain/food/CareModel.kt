package com.example.mypet.domain.food

import com.example.mypet.domain.alarm.AlarmSwitchModel

const val FOOD_KEY = "food"
const val START_KEY = "start"
const val REPEAT_KEY = "repeat"
const val END_KEY = "end"
const val ALARM_HEADER_KEY = "alarm-header"
const val ALARM_KEY = "alarm"

sealed class CareModel(open val key: String)

data class CareFoodModel(
    val text: String,
) : CareModel(FOOD_KEY)

data class CareStartModel(
    val description: String,
) : CareModel(START_KEY)

data class CareRepeatModel(
    val description: String,
) : CareModel(REPEAT_KEY)

data class CareEndModel(
    val description: String,
) : CareModel(END_KEY)

data class CareAlarmHeaderModel(
    val text: String,
) : CareModel(ALARM_HEADER_KEY)

data class CareAlarmModel(
    val foodId: Int,
    val foodTitle: String,
    val foodRation: String?,
    val alarmId: Int?,
    val alarmHour: Int?,
    val alarmMinute: Int?,
    val alarmIsActive: Boolean?
) : CareModel("$ALARM_KEY-$foodId") {
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