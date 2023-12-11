package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalCareEntity
import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity


@Dao
interface LocalCareDao {
    @Query("SELECT * FROM care WHERE pet_id = :petId AND care_type_ordinal = :careTypeOrdinal LIMIT 1")
    fun getLocalCareEntity(petId: Int, careTypeOrdinal: Int): LocalCareEntity?

    @Query("SELECT * FROM start WHERE care_id = :careId LIMIT 1")
    fun getLocalStartEntity(careId: Int): LocalStartEntity?

    @Query("SELECT * FROM repeat WHERE care_id = :careId LIMIT 1")
    fun getLocalRepeatEntity(careId: Int): LocalRepeatEntity?

    @Query("SELECT * FROM alarm WHERE care_id = :careId")
    fun getLocalAlarmEntities(careId: Int): List<LocalAlarmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLocalCareEntity(localCareEntity: LocalCareEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLocalStartEntity(localStartEntity: LocalStartEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLocalRepeatEntity(localRepeatEntity: LocalRepeatEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLocalAlarmEntity(localAlarmEntity: LocalAlarmEntity): Long
}