package com.example.mypet.domain.care

data class CareStartModel(
    val id: Int,
    var timeInMillis: Long,
    var hour: Int,
    var minute: Int,
) : CareModel(CARE_ADAPTER_START_KEY)