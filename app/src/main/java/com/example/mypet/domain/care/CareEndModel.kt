package com.example.mypet.domain.care

data class CareEndModel(
    val id: Int,

    var typeOrdinal: Int?,
    var afterTimes: Int?,
    var afterDate: Long?,
) : CareModel(CARE_ADAPTER_END_KEY)