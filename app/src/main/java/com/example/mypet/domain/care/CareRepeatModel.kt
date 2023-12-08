package com.example.mypet.domain.care

data class CareRepeatModel(
    val id: Int,

    val typeOrdinal: Int? = null,
    val interval: Int? = null,

    val isMonday: Boolean? = null,
    val isTuesday: Boolean? = null,
    val isWednesday: Boolean? = null,
    val isThursday: Boolean? = null,
    val isFriday: Boolean? = null,
    val isSaturday: Boolean? = null,
    val isSunday: Boolean? = null,

    val endDay: Int? = null,
    val endMonth: Int? = null,
    val endYear: Int? = null,
    val endCount: Int? = null,
) : CareAdapterModel(CARE_ADAPTER_REPEAT_KEY)