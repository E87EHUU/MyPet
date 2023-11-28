package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.entity.LocalCareEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalCareDao {
    @Query("SELECT * FROM care WHERE id = :careId LIMIT 1")
    fun getCareModel(careId: Int): Flow<LocalCareEntity?>
}