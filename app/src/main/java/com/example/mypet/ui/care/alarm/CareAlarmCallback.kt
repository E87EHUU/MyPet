package com.example.mypet.ui.care.alarm

import com.example.mypet.domain.care.alarm.CareAlarmDetailModel

interface CareAlarmCallback {
    fun onClickAlarm(careAlarmDetailModel: CareAlarmDetailModel? = null)
    fun onSwitchAlarmStart(careAlarmDetailModel: CareAlarmDetailModel)
}