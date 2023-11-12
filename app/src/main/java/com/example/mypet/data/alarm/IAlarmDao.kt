package com.example.mypet.data.alarm

interface IAlarmDao {
    suspend fun setAlarm(alarmModel: AlarmModel)
    suspend fun removeAlarm(alarmId: Int)
}