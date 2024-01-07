package com.example.mypet.ui.care.alarm.main

import com.example.mypet.domain.care.alarm.CareAlarmDetailModel

interface CareAlarmMainCallback {
    fun onClickAlarm(careAlarmDetailMainModel: CareAlarmDetailModel)
    fun onClickAlarmAdd()
    fun onClickAlarmDelete(careAlarmDetailMainModel: CareAlarmDetailModel)
}