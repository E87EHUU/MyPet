package com.example.mypet.data.local.room.model.food

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity
import com.example.mypet.data.local.room.entity.PET_FOOD_TABLE

data class LocalFoodAlarmModel(
    @ColumnInfo(name = "${PET_FOOD_TABLE}_${LocalDatabase.ID}")
    val foodId: Int,
    @ColumnInfo(name = "${PET_FOOD_TABLE}_${LocalDatabase.TITLE}")
    val foodTitle: String,
    @ColumnInfo(name = "${PET_FOOD_TABLE}_${LocalPetFoodEntity.RATION}")
    val foodRation: String?,
    @ColumnInfo(name = "${ALARM_TABLE}_${LocalDatabase.ID}")
    val alarmId: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${LocalAlarmEntity.HOUR}")
    val alarmHour: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${LocalAlarmEntity.MINUTE}")
    val alarmMinute: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${LocalAlarmEntity.IS_ACTIVE}")
    val alarmIsActive: Boolean?
)