package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.domain.pet.PetFoodAlarmModel
import com.example.mypet.ui.food.toAppTime

data class LocalPetFoodAlarmModel(
    @ColumnInfo(name = "${ALARM_TABLE}_$ID")
    val alarmId: Int,
    @ColumnInfo(name = "${ALARM_TABLE}_$HOUR")
    val alarmHour: Int,
    @ColumnInfo(name = "${ALARM_TABLE}_$MINUTE")
    val alarmMinute: Int,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_ACTIVE")
    val alarmIsActive: Boolean
) {
    fun toPetFoodAlarmModel() =
        PetFoodAlarmModel(
            alarmId = alarmId,
            time = toAppTime(alarmHour, alarmMinute),
            isActive = alarmIsActive
        )
}