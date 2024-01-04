package com.example.mypet.ui.care.alarm.main

import com.example.mypet.domain.care.alarm.CareAlarmDetailMainModel

interface CareAlarmMainCallback {
    fun onClickAlarm(careAlarmDetailMainModel: CareAlarmDetailMainModel)
    fun onClickAlarmAdd()
    fun onClickAlarmDelete(careAlarmDetailMainModel: CareAlarmDetailMainModel)
}