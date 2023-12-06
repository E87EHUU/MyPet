package com.example.mypet.domain.care.repeat

import java.util.Calendar

data class CareRepeatDetailModel(
    var timeInMillis: Long = Calendar.getInstance().timeInMillis,
)