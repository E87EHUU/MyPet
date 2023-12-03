package com.example.mypet.domain.care

data class CareMainModel(
    val careType: CareTypes,
) : CareAdapterModel(CARE_ADAPTER_MAIN_KEY)