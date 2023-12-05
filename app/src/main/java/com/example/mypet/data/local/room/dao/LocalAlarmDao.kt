package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query


@Dao
interface LocalAlarmDao {
    @Query("UPDATE alarm SET is_active = :alarmIsActive WHERE id = :alarmId")
    fun switchAlarmState(alarmId: Int, alarmIsActive: Boolean): Int
}