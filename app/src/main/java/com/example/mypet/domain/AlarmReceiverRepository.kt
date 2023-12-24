package com.example.mypet.domain

import com.example.mypet.domain.alarm.receiver.AlarmReceiverModel

interface AlarmReceiverRepository {
    suspend fun getAlarmReceiverModel(alarmId: Int): AlarmReceiverModel?
}