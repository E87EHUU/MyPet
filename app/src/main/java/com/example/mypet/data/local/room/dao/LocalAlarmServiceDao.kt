package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
import com.example.mypet.data.local.room.LocalDatabase.Companion.HOUR
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.MINUTE
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.CARE_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_DELAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_VIBRATION
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.RINGTONE_PATH
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.CARE_TYPE_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity
import com.example.mypet.data.local.room.entity.PET_TABLE
import com.example.mypet.data.local.room.model.alarm.LocalAlarmServiceModel


@Dao
interface LocalAlarmServiceDao {
    @Query(
        "SELECT " +
                "p.id ${PET_TABLE}_$ID, " +
                "p.avatar_path ${PET_TABLE}_$AVATAR_PATH, " +
                "p.name ${PET_TABLE}_$NAME, " +
                "p.kind_ordinal ${PET_TABLE}_$KIND_ORDINAL, " +
                "p.breed_ordinal ${PET_TABLE}_$BREED_ORDINAL, " +
                "c.care_type_ordinal ${CARE_TABLE}_$CARE_TYPE_ORDINAL, " +
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.description ${ALARM_TABLE}_$DESCRIPTION, " +
                "a.hour ${ALARM_TABLE}_$HOUR, " +
                "a.minute ${ALARM_TABLE}_$MINUTE, " +
                "a.ringtone_path ${ALARM_TABLE}_$RINGTONE_PATH, " +
                "a.is_vibration ${ALARM_TABLE}_$IS_VIBRATION, " +
                "a.is_delay ${ALARM_TABLE}_$IS_DELAY, " +
                "a.is_active ${ALARM_TABLE}_$IS_ACTIVE " +
                "FROM alarm a " +
                "LEFT JOIN care c ON c.id = a.care_id " +
                "LEFT JOIN pet p ON p.id = c.pet_id " +
                "WHERE a.id = :alarmId " +
                "LIMIT 1"
    )
    fun getLocalAlarmServiceModel(alarmId: Int): LocalAlarmServiceModel?

    @Query("SELECT * FROM alarm WHERE id = :alarmId")
    fun getLocalAlarmEntity(alarmId: Int): LocalAlarmEntity?

    @Query(
        "SELECT s.* FROM alarm a " +
                "LEFT JOIN start s ON s.care_id = a.care_id " +
                " WHERE a.id = :alarmId"
    )
    fun getLocalStartEntity(alarmId: Int): LocalStartEntity?

    @Query(
        "SELECT r.* FROM alarm a " +
                "LEFT JOIN repeat r ON r.care_id = a.care_id " +
                " WHERE a.id = :alarmId"
    )
    fun getLocalRepeatEntity(alarmId: Int): LocalRepeatEntity?

    @Update
    fun updateLocalAlarmEntity(localAlarmEntity: LocalAlarmEntity): Int
}