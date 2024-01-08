package com.example.mypet.ui.care.alarm

import com.example.mypet.domain.care.alarm.CareAlarmDetailModel

interface CareAlarmCallback {
    fun onClickAlarm(careAlarmDetailMainModel: CareAlarmDetailModel? = null)
    fun onClickAlarmDelete(careAlarmDetailMainModel: CareAlarmDetailModel)
}