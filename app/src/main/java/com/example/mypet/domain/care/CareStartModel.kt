package com.example.mypet.domain.care

data class CareStartModel(
    var date: String,
    var time: String,
) : CareAdapterModel(CARE_ADAPTER_START_KEY)