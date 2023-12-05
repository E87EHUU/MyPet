package com.example.mypet.domain.care

import com.example.mypet.domain.alarm.AlarmMinModel

data class CareAlarmModel(
    val alarms: List<AlarmMinModel>
) : CareAdapterModel(CARE_ADAPTER_ALARM_KEY)