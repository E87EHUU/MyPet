package com.example.mypet.domain.food.detail

import com.example.mypet.data.alarm.AlarmModel
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity

data class SaveAndSetFoodDetailModel(
    val myId: Int,

    val foodId: Int? = null,
    val foodTitle: String,
    val foodRation: String?,

    val alarmId: Int? = null,
    val alarmHour: Int,
    val alarmMinute: Int,
    val alarmIsRepeatMonday: Boolean,
    val alarmIsRepeatTuesday: Boolean,
    val alarmIsRepeatWednesday: Boolean,
    val alarmIsRepeatThursday: Boolean,
    val alarmIsRepeatFriday: Boolean,
    val alarmIsRepeatSaturday: Boolean,
    val alarmIsRepeatSunday: Boolean,
    val alarmRingtonePath: String?,
    val alarmIsVibration: Boolean,
    val alarmIsDelay: Boolean,
) {
    fun toLocalPetFoodEntity() =
        LocalPetFoodEntity(
            petMyId = myId,
            alarmId = alarmId,
            title = foodTitle,
            ration = foodRation
        )

    fun toLocalAlarmEntity() =
        LocalAlarmEntity(
            alarmId ?: DEFAULT_ID,
            alarmHour,
            alarmMinute,
            alarmIsRepeatMonday,
            alarmIsRepeatTuesday,
            alarmIsRepeatWednesday,
            alarmIsRepeatThursday,
            alarmIsRepeatFriday,
            alarmIsRepeatSaturday,
            alarmIsRepeatSunday,
            alarmRingtonePath,
            alarmIsVibration,
            alarmIsDelay
        )

    fun toAlarmModel(): AlarmModel? {
        val alarmId = alarmId ?: return null

        return AlarmModel(
            id = alarmId,
            hour = alarmHour,
            minute = alarmMinute,
            isRepeatMonday = alarmIsRepeatMonday,
            isRepeatTuesday = alarmIsRepeatTuesday,
            isRepeatWednesday = alarmIsRepeatWednesday,
            isRepeatThursday = alarmIsRepeatThursday,
            isRepeatFriday = alarmIsRepeatFriday,
            isRepeatSaturday = alarmIsRepeatSaturday,
            isRepeatSunday = alarmIsRepeatSunday,
        )
    }
}