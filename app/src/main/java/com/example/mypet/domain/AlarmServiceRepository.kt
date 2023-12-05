package com.example.mypet.domain

import com.example.mypet.domain.alarm.service.AlarmServiceModel

interface AlarmServiceRepository {
    suspend fun getAlarmServiceModel(alarmId: Int): AlarmServiceModel?
    suspend fun stopAlarm(localAlarmServiceModel: AlarmServiceModel)
    suspend fun setDelayAlarm(localAlarmServiceModel: AlarmServiceModel)
}