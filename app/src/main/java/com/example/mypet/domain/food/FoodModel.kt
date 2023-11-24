package com.example.mypet.domain.food

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalPetMyEntity
import com.example.mypet.data.local.room.entity.PET_FOOD_TABLE
import com.example.mypet.domain.alarm.AlarmSwitchModel

data class FoodModel(
    @ColumnInfo(name = "${PET_FOOD_TABLE}_$ID")
    val foodId: Int,
    @ColumnInfo(name = LocalDatabase.TITLE)
    val foodTitle: String,
    @ColumnInfo(name = "${ALARM_TABLE}_$ID")
    val alarmId: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${LocalAlarmEntity.HOUR}")
    val alarmHour: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${LocalAlarmEntity.MINUTE}")
    val alarmMinute: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${LocalPetMyEntity.IS_ACTIVE}")
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