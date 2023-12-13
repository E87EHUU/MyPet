package com.example.mypet.data.local.room.model.alarm

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
import com.example.mypet.data.local.room.entity.PET_TABLE

data class LocalAlarmServiceModel(
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

    @ColumnInfo(name = "${ALARM_TABLE}_$RINGTONE_PATH")
    val alarmRingtonePath: String?,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_VIBRATION")
    val alarmIsVibration: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_DELAY")
    val alarmIsDelay: Boolean,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_ACTIVE")
    val alarmIsActive: Boolean,
)