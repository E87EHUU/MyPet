package com.example.mypet.domain.food.detail.alarm

import android.net.Uri
import com.example.mypet.data.alarm.AlarmModel
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity

data class SaveFoodDetailAlarmAndSetAlarm(
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
    val alarmMelodyURI: Uri?,
    val alarmIsVibration: Boolean,
    val alarmIsDelay: Boolean,
) {
    fun toLocalPetFoodEntity() =
        LocalPetFoodEntity(
            id = foodId ?: DEFAULT_ID,
            petMyId = petMyId,
            alarmId = alarmId,
            title = foodName
        )

    fun toLocalAlarmEntity() =
        LocalAlarmEntity(
            id = alarmId ?: DEFAULT_ID,
            hour = alarmHour,
            minute = alarmMinute,
            isRepeatMonday = alarmRepeatMonday,
            isRepeatTuesday = alarmRepeatTuesday,
            isRepeatWednesday = alarmRepeatWednesday,
            isRepeatThursday = alarmRepeatThursday,
            isRepeatFriday = alarmRepeatFriday,
            isRepeatSaturday = alarmRepeatSaturday,
            isRepeatSunday = alarmRepeatSunday,
            ringtoneURI = alarmMelodyURI?.path,
            isVibration = alarmIsVibration,
            isDelay = alarmIsDelay,
            isActive = true
        )

    fun toAlarmModel(): AlarmModel? {
        if (alarmId == null) return null

        return AlarmModel(
            id = alarmId,
            hour = alarmHour,
            minute = alarmMinute,
        )
    }
}