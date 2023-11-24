package com.example.mypet.domain.alarm.service

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.TITLE
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.DAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_COUNT
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_DAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_MONTH
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_YEAR
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
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MONTH
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_INTERVAL
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_TYPE_ORDINAL
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.RINGTONE_PATH
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.YEAR
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.KIND_ORDINAL
import com.example.mypet.domain.alarm.AlarmModel

data class AlarmServiceModel(
    @ColumnInfo(name = ID)
    val anyId: Int,

    @ColumnInfo(name = AVATAR_PATH)
    val anyAvatarPath: String?,
    @ColumnInfo(name = TITLE)
    val anyTitle: String,
    @ColumnInfo(name = DESCRIPTION)
    val anyDescription: String,

    @ColumnInfo(name = KIND_ORDINAL)
    val kindOrdinal: Int,
    @ColumnInfo(name = BREED_ORDINAL)
    val breedOrdinal: Int?,

    @ColumnInfo(name = "${ALARM_TABLE}_$ID")
    val alarmId: Int,
    @ColumnInfo(name = "${ALARM_TABLE}_$HOUR")
    val alarmHour: Int,
    @ColumnInfo(name = "${ALARM_TABLE}_$MINUTE")
    val alarmMinute: Int,
    @ColumnInfo(name = "${ALARM_TABLE}_$DAY")
    val alarmDay: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$MONTH")
    val alarmMonth: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$YEAR")
    val alarmYear: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$RINGTONE_PATH")
    val alarmRingtonePath: String?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_VIBRATION")
    val alarmIsVibration: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_DELAY")
    val alarmIsDelay: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_ACTIVE")
    val alarmIsActive: Boolean,

    @ColumnInfo(name = "${ALARM_TABLE}_$REPEAT_TYPE_ORDINAL")
    val alarmRepeatTypeOrdinal: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$REPEAT_INTERVAL")
    val alarmRepeatInterval: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_MONDAY")
    val alarmIsRepeatMonday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_TUESDAY")
    val alarmIsRepeatTuesday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_WEDNESDAY")
    val alarmIsRepeatWednesday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_THURSDAY")
    val alarmIsRepeatThursday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_FRIDAY")
    val alarmIsRepeatFriday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_SATURDAY")
    val alarmIsRepeatSaturday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_SUNDAY")
    val alarmIsRepeatSunday: Boolean?,
    @ColumnInfo(name = "${ALARM_TABLE}_$END_DAY")
    val alarmEndDay: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$END_MONTH")
    val alarmEndMonth: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$END_YEAR")
    val alarmEndYear: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_$END_COUNT")
    val alarmEndCount: Int?,
) {
    fun toAlarmModel(delayTime: Int? = null) =
        AlarmModel(
            id = alarmId,
            hour = alarmHour,
            minute = alarmMinute,
            day = alarmDay,
            month = alarmMonth,
            year = alarmYear,
            ringtonePath = alarmRingtonePath,
            isVibration = alarmIsVibration,
            isDelay = alarmIsDelay,
            delayTime = delayTime,
            repeatTypeOrdinal = alarmRepeatTypeOrdinal,
            repeatInterval = alarmRepeatInterval,
            isRepeatMonday = alarmIsRepeatMonday,
            isRepeatTuesday = alarmIsRepeatTuesday,
            isRepeatWednesday = alarmIsRepeatWednesday,
            isRepeatThursday = alarmIsRepeatThursday,
            isRepeatFriday = alarmIsRepeatFriday,
            isRepeatSaturday = alarmIsRepeatSaturday,
            isRepeatSunday = alarmIsRepeatSunday,
            endDay = alarmEndDay,
            endMonth = alarmEndMonth,
            endYear = alarmEndYear,
            endCount = alarmEndCount,
        )
}