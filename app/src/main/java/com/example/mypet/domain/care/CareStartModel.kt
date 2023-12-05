package com.example.mypet.domain.care

import java.time.LocalDateTime
import java.util.Calendar

data class CareStartModel(
    var timeInMillis: Long = Calendar.getInstance().timeInMillis,
    var hour: Int = LocalDateTime.now().hour,
    var minute: Int = LocalDateTime.now().minute,
) : CareAdapterModel(CARE_ADAPTER_START_KEY)