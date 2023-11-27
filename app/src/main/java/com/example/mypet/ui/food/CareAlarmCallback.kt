package com.example.mypet.ui.food

import com.example.mypet.domain.food.CareAlarmModel

interface CareAlarmCallback {
    fun onItemClick(careAlarmModel: CareAlarmModel)
    fun onSwitchActive(careAlarmModel: CareAlarmModel)
}