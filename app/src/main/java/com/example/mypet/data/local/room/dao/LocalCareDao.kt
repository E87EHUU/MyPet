package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.entity.LocalCareEntity
import com.example.mypet.data.local.room.model.LocalAlarmMinModel
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalCareDao {
    @Query("SELECT * FROM care WHERE id = :careId LIMIT 1")
    fun getLocalCareEntity(careId: Int): Flow<LocalCareEntity?>

    @Query("SELECT id, hour, minute, is_active FROM alarm WHERE care_id = :careId")
    fun getLocalAlarmMinModels(careId: Int): Flow<List<LocalAlarmMinModel>>
}