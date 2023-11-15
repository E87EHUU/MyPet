package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.TITLE
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.PET_FOOD_TABLE

data class LocalFoodModel(
    @ColumnInfo(name = "${PET_FOOD_TABLE}_$ID")
    val foodId: Int,
    @ColumnInfo(name = TITLE)
    val foodTitle: String,

    @ColumnInfo(name = "${ALARM_TABLE}_$ID")
    val alarmId: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$HOUR")
    val alarmHour: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$MINUTE")
    val alarmMinute: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_ACTIVE")
    val alarmIsActive: Boolean?
)