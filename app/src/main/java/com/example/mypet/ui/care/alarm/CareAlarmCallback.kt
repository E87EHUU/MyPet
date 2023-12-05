package com.example.mypet.ui.care.alarm

import com.example.mypet.domain.alarm.AlarmMinModel

interface CareAlarmCallback {
    fun onClickAlarm(alarmMinModel: AlarmMinModel)
    fun onSwitchAlarmStart(alarmMinModel: AlarmMinModel)
}