package com.example.mypet.data.alarm

import com.example.mypet.data.alarm.model.AlarmModel

interface AlarmDao {
    suspend fun setAlarm(alarmModel: AlarmModel)
}