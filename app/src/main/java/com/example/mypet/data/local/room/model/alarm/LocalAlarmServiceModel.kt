package com.example.mypet.data.local.room.model.alarm

import androidx.room.ColumnInfo
import com.example.mypet.data.alarm.AlarmModel
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
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
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.KIND_ORDINAL

data class LocalAlarmServiceModel(
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
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_MONDAY")
    val alarmIsRepeatMonday: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_TUESDAY")
    val alarmIsRepeatTuesday: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_WEDNESDAY")
    val alarmIsRepeatWednesday: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_THURSDAY")
    val alarmIsRepeatThursday: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_FRIDAY")
    val alarmIsRepeatFriday: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_SATURDAY")
    val alarmIsRepeatSaturday: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_SUNDAY")
    val alarmIsRepeatSunday: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$RINGTONE_PATH")
    val alarmRingtonePath: String?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_VIBRATION")
    val alarmIsVibration: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_DELAY")
    val alarmIsDelay: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_ACTIVE")
    val alarmIsActive: Boolean
) {
    fun toAlarmModel(delay: Int? = null) =
        AlarmModel(
            alarmId,
            alarmHour,
            alarmMinute,
            alarmIsRepeatMonday,
            alarmIsRepeatTuesday,
            alarmIsRepeatWednesday,
            alarmIsRepeatThursday,
            alarmIsRepeatFriday,
            alarmIsRepeatSaturday,
            alarmIsRepeatSunday,
            delayTime = delay
        )
}