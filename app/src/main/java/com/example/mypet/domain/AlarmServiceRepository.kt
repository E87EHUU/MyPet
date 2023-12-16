package com.example.mypet.domain

import com.example.mypet.domain.alarm.service.AlarmServiceModel

interface AlarmServiceRepository {
    suspend fun getAlarmServiceModel(alarmId: Int): AlarmServiceModel?
    suspend fun stopAlarm(alarmId: Int)
    suspend fun setDelayAlarm(alarmId: Int)
}