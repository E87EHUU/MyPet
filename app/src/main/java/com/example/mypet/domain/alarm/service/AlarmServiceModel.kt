package com.example.mypet.domain.alarm.service

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
import com.example.mypet.data.local.room.LocalDatabase.Companion.HOUR
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.MINUTE
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_DELAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_VIBRATION
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.RINGTONE_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.INTERVAL
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_FRIDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_MONDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_SATURDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_SUNDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_THURSDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_TUESDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_WEDNESDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.TYPE_ORDINAL
import com.example.mypet.data.local.room.entity.PET_TABLE
import com.example.mypet.data.local.room.entity.REPEAT_TABLE
import com.example.mypet.domain.alarm.AlarmModel

data class AlarmServiceModel(
    @ColumnInfo(name = "${PET_TABLE}_$ID")
    val petId: Int,
    @ColumnInfo(name = "${PET_TABLE}_$AVATAR_PATH")
    val petAvatarPath: String?,
    @ColumnInfo(name = "${PET_TABLE}_$KIND_ORDINAL")
    val petKindOrdinal: Int,
    @ColumnInfo(name = "${PET_TABLE}_$BREED_ORDINAL")
    val petBreedOrdinal: Int?,

    @ColumnInfo(name = "${ALARM_TABLE}_$ID")
    val alarmId: Int,
    @ColumnInfo(name = "${ALARM_TABLE}_$DESCRIPTION")
    val alarmDescription: String?,
    @ColumnInfo(name = "${ALARM_TABLE}_$HOUR")
    val alarmHour: Int,
    @ColumnInfo(name = "${ALARM_TABLE}_$MINUTE")
    val alarmMinute: Int,
    /*    @ColumnInfo(name = "${ALARM_TABLE}_$DAY")
        val alarmDay: Int?,
        @ColumnInfo(name = "${ALARM_TABLE}_$MONTH")
        val alarmMonth: Int?,
        @ColumnInfo(name = "${ALARM_TABLE}_$YEAR")
        val alarmYear: Int?,*/
    @ColumnInfo(name = "${ALARM_TABLE}_$RINGTONE_PATH")
    val alarmRingtonePath: String?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_VIBRATION")
    val alarmIsVibration: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_DELAY")
    val alarmIsDelay: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_ACTIVE")
    val alarmIsActive: Boolean,

    @ColumnInfo(name = "${REPEAT_TABLE}_$TYPE_ORDINAL")
    val alarmRepeatTypeOrdinal: Int?,
    @ColumnInfo(name = "${REPEAT_TABLE}_$INTERVAL")
    val alarmRepeatInterval: Int?,
    @ColumnInfo(name = "${REPEAT_TABLE}_$IS_MONDAY")
    val alarmIsRepeatMonday: Boolean?,
    @ColumnInfo(name = "${REPEAT_TABLE}_$IS_TUESDAY")
    val alarmIsRepeatTuesday: Boolean?,
    @ColumnInfo(name = "${REPEAT_TABLE}_$IS_WEDNESDAY")
    val alarmIsRepeatWednesday: Boolean?,
    @ColumnInfo(name = "${REPEAT_TABLE}_$IS_THURSDAY")
    val alarmIsRepeatThursday: Boolean?,
    @ColumnInfo(name = "${REPEAT_TABLE}_$IS_FRIDAY")
    val alarmIsRepeatFriday: Boolean?,
    @ColumnInfo(name = "${REPEAT_TABLE}_$IS_SATURDAY")
    val alarmIsRepeatSaturday: Boolean?,
    @ColumnInfo(name = "${REPEAT_TABLE}_$IS_SUNDAY")
    val alarmIsRepeatSunday: Boolean?,
    /*    @ColumnInfo(name = "${ALARM_TABLE}_$END_DAY")
        val alarmEndDay: Int?,
        @ColumnInfo(name = "${ALARM_TABLE}_$END_MONTH")
        val alarmEndMonth: Int?,
        @ColumnInfo(name = "${ALARM_TABLE}_$END_YEAR")
        val alarmEndYear: Int?,
        @ColumnInfo(name = "${ALARM_TABLE}_$END_COUNT")
        val alarmEndCount: Int?,*/
) {
    fun toAlarmModel(delayTime: Int? = null) =
        AlarmModel(
            id = alarmId,
            hour = alarmHour,
            minute = alarmMinute,
            /*            day = alarmDay,
                        month = alarmMonth,
                        year = alarmYear,*/
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
            /*            endDay = alarmEndDay,
                        endMonth = alarmEndMonth,
                        endYear = alarmEndYear,
                        endCount = alarmEndCount,*/
        )
}