package com.example.mypet.data.local.room.model.food

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE

data class LocalFoodAlarmModel(
    @ColumnInfo(name = "${ALARM_TABLE}_$ID")
    val alarmId: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$DESCRIPTION")
    val alarmDescription: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$HOUR")
    val alarmHour: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$MINUTE")
    val alarmMinute: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_ACTIVE")
    val alarmIsActive: Boolean?
)