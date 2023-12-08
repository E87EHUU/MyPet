package com.example.mypet.domain.care

import com.example.mypet.domain.care.alarm.CareAlarmDetailModel

data class CareAlarmModel(
    val alarms: List<CareAlarmDetailModel>
) : CareAdapterModel(CARE_ADAPTER_ALARM_KEY)