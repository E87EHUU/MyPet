package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_DELAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_VIBRATION
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MELODY_URI
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_FRIDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_MONDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_SATURDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_SUNDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_THURSDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_TUESDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_WEDNESDAY

data class LocalFoodDetailAlarmModel(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = NAME)
    val name: String,

    @ColumnInfo(name = "${ALARM_TABLE}_${ID}")
    val alarmId: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${HOUR}")
    val alarmHour: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${MINUTE}")
    val alarmMinute: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${REPEAT_MONDAY}")
    val alarmRepeatMonday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_${REPEAT_TUESDAY}")
    val alarmRepeatTuesday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_${REPEAT_WEDNESDAY}")
    val alarmRepeatWednesday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_${REPEAT_THURSDAY}")
    val alarmRepeatThursday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_${REPEAT_FRIDAY}")
    val alarmRepeatFriday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_${REPEAT_SATURDAY}")
    val alarmRepeatSaturday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_${REPEAT_SUNDAY}")
    val alarmRepeatSunday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_${MELODY_URI}")
    val alarmMelodyURI: String?,
    @ColumnInfo(name = "${ALARM_TABLE}_${IS_VIBRATION}")
    val alarmIsVibration: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_${IS_DELAY}")
    val alarmIsDelay: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_${IS_ACTIVE}")
    val alarmIsActive: Boolean?
)