package com.example.mypet.domain.care

data class CareRepeatModel(
    var message: String,
) : CareAdapterModel(CARE_ADAPTER_REPEAT_KEY)