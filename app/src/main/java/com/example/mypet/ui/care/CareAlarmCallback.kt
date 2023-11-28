package com.example.mypet.ui.care

import com.example.mypet.domain.care.CareViewHolderAlarmModel

interface CareAlarmCallback {
    fun onItemClick(careAlarmModel: CareViewHolderAlarmModel)
    fun onSwitchActive(careAlarmModel: CareViewHolderAlarmModel)
}