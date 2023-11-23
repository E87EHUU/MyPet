package com.example.mypet.domain

import com.example.mypet.data.local.room.model.alarm.LocalAlarmServiceModel

interface AlarmServiceRepository {
    suspend fun getAlarmServiceModel(alarmId: Int): LocalAlarmServiceModel?
    suspend fun stopAlarm(localAlarmServiceModel: LocalAlarmServiceModel)
    suspend fun setDelayAlarm(localAlarmServiceModel: LocalAlarmServiceModel)
}