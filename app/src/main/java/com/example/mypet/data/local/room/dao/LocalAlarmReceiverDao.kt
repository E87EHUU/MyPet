package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.CARE_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.CARE_TYPE_ORDINAL
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.DOSE
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.TITLE
import com.example.mypet.data.local.room.entity.LocalEndEntity
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity
import com.example.mypet.data.local.room.entity.PET_TABLE
import com.example.mypet.data.local.room.model.alarm.LocalAlarmReceiverModel


@Dao
interface LocalAlarmReceiverDao {
    @Query(
        "SELECT " +
                "p.id ${PET_TABLE}_$ID, " +
                "p.avatar_path ${PET_TABLE}_$AVATAR_PATH, " +
                "p.name ${PET_TABLE}_$NAME, " +
                "p.kind_ordinal ${PET_TABLE}_$KIND_ORDINAL, " +
                "p.breed_ordinal ${PET_TABLE}_$BREED_ORDINAL, " +
                "c.care_type_ordinal ${CARE_TABLE}_$CARE_TYPE_ORDINAL, " +
                "c.title ${CARE_TABLE}_$TITLE, " +
                "c.dose ${CARE_TABLE}_$DOSE, " +
                "a.id ${ALARM_TABLE}_$ID " +
                "FROM alarm a " +
                "LEFT JOIN care c ON c.id = a.care_id " +
                "LEFT JOIN pet p ON p.id = c.pet_id " +
                "WHERE a.id = :alarmId " +
                "LIMIT 1"
    )
    fun getLocalAlarmReceiverModel(alarmId: Int): LocalAlarmReceiverModel?

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

    @Query(
        "SELECT e.* FROM alarm a " +
                "LEFT JOIN ending e ON e.care_id = a.care_id " +
                " WHERE a.id = :alarmId"
    )
    fun getLocalEndEntity(alarmId: Int): LocalEndEntity?

    @Update
    fun updateLocalAlarmEntity(localAlarmEntity: LocalAlarmEntity): Int
}