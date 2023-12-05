package com.example.mypet.domain

import com.example.mypet.domain.alarm.AlarmSwitchModel

interface AlarmRepository {
    suspend fun switch(alarmSwitchModel: AlarmSwitchModel)
}