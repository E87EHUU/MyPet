package com.example.mypet.domain.care

data class CareStartModel(
    var timeInMillis: Long,
    var hour: Int,
    var minute: Int,
) : CareAdapterModel(CARE_ADAPTER_START_KEY)