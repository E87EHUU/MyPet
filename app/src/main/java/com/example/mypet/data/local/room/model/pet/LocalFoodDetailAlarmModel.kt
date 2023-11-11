package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ICON_RES_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.TITLE
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_DELAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_FRIDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_MONDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_SATURDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_SUNDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_THURSDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_TUESDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_WEDNESDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_VIBRATION
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.RINGTONE_PATH

data class LocalFoodDetailAlarmModel(
    @ColumnInfo(name = ID)
    val foodId: Int,
    @ColumnInfo(name = TITLE)
    val title: String,

    @ColumnInfo(name = ICON_RES_ID)
    val iconResId: Int,

    @ColumnInfo(name = "${ALARM_TABLE}_${ID}")
    val alarmId: Int,
    @ColumnInfo(name = HOUR)
    val hour: Int,
    @ColumnInfo(name = MINUTE)
    val minute: Int,
    @ColumnInfo(name = IS_REPEAT_MONDAY)
    val isRepeatMonday: Boolean,
    @ColumnInfo(name = IS_REPEAT_TUESDAY)
    val isRepeatTuesday: Boolean,
    @ColumnInfo(name = IS_REPEAT_WEDNESDAY)
    val isRepeatWednesday: Boolean,
    @ColumnInfo(name = IS_REPEAT_THURSDAY)
    val isRepeatThursday: Boolean,
    @ColumnInfo(name = IS_REPEAT_FRIDAY)
    val isRepeatFriday: Boolean,
    @ColumnInfo(name = IS_REPEAT_SATURDAY)
    val isRepeatSaturday: Boolean,
    @ColumnInfo(name = IS_REPEAT_SUNDAY)
    val isRepeatSunday: Boolean,
    @ColumnInfo(name = RINGTONE_PATH)
    val ringtoneUri: String?,
    @ColumnInfo(name = IS_VIBRATION)
    val isVibration: Boolean,
    @ColumnInfo(name = IS_DELAY)
    val isDelay: Boolean,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean
)