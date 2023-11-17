package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.entity.LocalAlarmEntity

@Dao
interface LocalBootCompleteDao {
    @Query("SELECT * FROM alarm WHERE is_active = 1")
    fun getAllAlarm(): List<LocalAlarmEntity>
}