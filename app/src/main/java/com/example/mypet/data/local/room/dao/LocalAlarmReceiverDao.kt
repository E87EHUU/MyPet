package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.CARE_TABLE
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.CARE_TYPE_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
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
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.description ${ALARM_TABLE}_$DESCRIPTION " +
                "FROM alarm a " +
                "LEFT JOIN care c ON c.id = a.care_id " +
                "LEFT JOIN pet p ON p.id = c.pet_id " +
                "WHERE a.id = :alarmId " +
                "LIMIT 1"
    )
    fun getLocalAlarmReceiverModel(alarmId: Int): LocalAlarmReceiverModel?
}