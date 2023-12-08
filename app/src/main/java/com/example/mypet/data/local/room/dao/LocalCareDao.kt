package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalCareEntity
import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity


@Dao
interface LocalCareDao {
    @Query("SELECT * FROM care WHERE id = :careId LIMIT 1")
    fun getLocalCareEntity(careId: Int): LocalCareEntity?

    @Query("SELECT * FROM start WHERE id = :careId LIMIT 1")
    fun getLocalStartEntity(careId: Int): LocalStartEntity?

    @Query("SELECT * FROM repeat WHERE id = :careId LIMIT 1")
    fun getLocalRepeatEntity(careId: Int): LocalRepeatEntity?

    @Query("SELECT * FROM alarm WHERE care_id = :careId")
    fun getLocalAlarmEntities(careId: Int): List<LocalAlarmEntity>
}