package com.example.mypet.domain.food.alarm

import com.example.mypet.data.alarm.AlarmModel

data class FoodAlarmModel(
    val foodId: Int,
    val title: String,

    val iconResId: Int,

    var alarmId: Int,
    val hour: Int,
    val minute: Int,
    val isRepeatMonday: Boolean,
    val isRepeatTuesday: Boolean,
    val isRepeatWednesday: Boolean,
    val isRepeatThursday: Boolean,
    val isRepeatFriday: Boolean,
    val isRepeatSaturday: Boolean,
    val isRepeatSunday: Boolean,
    val ringtonePath: String?,
    val isVibration: Boolean,
    val isDelay: Boolean,
    val isActive: Boolean,

    val delayMinute: Int = 1,
) {
    fun toDelayAlarmModel() =
        AlarmModel(
            id = alarmId,
            hour,
            minute,
            isRepeatMonday,
            isRepeatTuesday,
            isRepeatWednesday,
            isRepeatThursday,
            isRepeatFriday,
            isRepeatSaturday,
            isRepeatSunday,
            delayMinute
        )
}