package com.example.mypet.domain.care

import java.time.LocalDateTime

data class CareStartModel(
    var date: String,
    var hour: Int = LocalDateTime.now().hour,
    var minute: Int = LocalDateTime.now().minute,
) : CareAdapterModel(CARE_ADAPTER_START_KEY)