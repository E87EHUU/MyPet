package com.example.mypet.domain.care

import com.example.mypet.domain.care.alarm.CareAlarmDetailModel

data class CareAlarmModel(
    var alarms: List<CareAlarmDetailModel>,
    val deletedAlarmIds: MutableList<Int> = mutableListOf(),
) : CareModel(CARE_ADAPTER_ALARM_KEY)