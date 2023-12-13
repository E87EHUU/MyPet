package com.example.mypet.domain.care

data class CareMainModel(
    val id: Int,
    val petId: Int,
    val careType: CareTypes,
    var note: String? = null,
    var progress: Int? = null,
) : CareModel(CARE_ADAPTER_MAIN_KEY)