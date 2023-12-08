package com.example.mypet.domain.care

data class CareMainModel(
    val careType: CareTypes,
    var note: String? = null,
    var progress: Int? = null,
) : CareAdapterModel(CARE_ADAPTER_MAIN_KEY)