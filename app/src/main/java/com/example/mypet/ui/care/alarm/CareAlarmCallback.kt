package com.example.mypet.ui.care.alarm

import com.example.mypet.domain.care.alarm.CareAlarmDetailMainModel

interface CareAlarmCallback {
    fun onClickAlarm(careAlarmDetailMainModel: CareAlarmDetailMainModel? = null)
    fun onClickDelete(careAlarmDetailMainModel: CareAlarmDetailMainModel)
}