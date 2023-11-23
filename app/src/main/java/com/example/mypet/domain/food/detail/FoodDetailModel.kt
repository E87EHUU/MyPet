package com.example.mypet.domain.food.detail

import android.net.Uri
import com.example.mypet.data.alarm.AlarmModel

data class FoodDetailModel(
    val foodId: Int,
    val foodTitle: String,
    val foodRation: String?,

    val myAvatarUri: Uri?,

    val kindOrdinal: Int,
    val breedOrdinal: Int?,

    val alarmId: Int?,
    val alarmHour: Int?,
    val alarmMinute: Int?,
    val alarmIsRepeatMonday: Boolean?,
    val alarmIsRepeatTuesday: Boolean?,
    val alarmIsRepeatWednesday: Boolean?,
    val alarmIsRepeatThursday: Boolean?,
    val alarmIsRepeatFriday: Boolean?,
    val alarmIsRepeatSaturday: Boolean?,
    val alarmIsRepeatSunday: Boolean?,
    val alarmRingtonePath: String?,
    val alarmIsVibration: Boolean?,
    val alarmIsDelay: Boolean?,
    val alarmIsActive: Boolean?,

    val delayMinute: Int = 1,
) {
/*    fun toDelayAlarmModel() =
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
        )*/

/*    fun toLocalPetFoodEntity() =
        LocalPetFoodEntity(
            id = foodId ?: LocalDatabase.DEFAULT_ID,
            petMyId = petMyId,
            alarmId = alarmId,
            title = foodName
        )*/

/*    fun toLocalAlarmEntity() =
        LocalAlarmEntity(
            id = alarmId ?: LocalDatabase.DEFAULT_ID,
            hour = alarmHour,
            minute = alarmMinute,
            isRepeatMonday = alarmRepeatMonday,
            isRepeatTuesday = alarmRepeatTuesday,
            isRepeatWednesday = alarmRepeatWednesday,
            isRepeatThursday = alarmRepeatThursday,
            isRepeatFriday = alarmRepeatFriday,
            isRepeatSaturday = alarmRepeatSaturday,
            isRepeatSunday = alarmRepeatSunday,
            ringtonePath = alarmRingtonePath,
            isVibration = alarmIsVibration,
            isDelay = alarmIsDelay,
            isActive = true
        )*/

    fun toAlarmModel(): AlarmModel? {
        val alarmId = alarmId ?: return null
        val alarmHour = alarmHour ?: return null
        val alarmMinute = alarmMinute ?: return null

        return AlarmModel(
            id = alarmId,
            hour = alarmHour,
            minute = alarmMinute,
        )
    }
}