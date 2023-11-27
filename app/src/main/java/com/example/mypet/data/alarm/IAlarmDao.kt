package com.example.mypet.data.alarm

import com.example.mypet.domain.alarm.AlarmModel

interface IAlarmDao {
    suspend fun setAlarm(alarmModel: AlarmModel)
    suspend fun removeAlarm(alarmId: Int)
}