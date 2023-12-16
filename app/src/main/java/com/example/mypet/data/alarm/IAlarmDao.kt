package com.example.mypet.data.alarm

interface IAlarmDao {
    suspend fun setAlarm(alarmId: Int, alarmTime: Long)
    suspend fun removeAlarm(alarmId: Int)
}