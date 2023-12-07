package com.example.mypet.domain.care.repeat

import java.util.Calendar

data class CareRepeatDetailModel(
    var intervalTimes: String = DEFAULT_INTERVAL_TIMES,
    var intervalOrdinal: Int = CareRepeatInterval.DAY.ordinal,

    var isMonday: Boolean = false,
    var isTuesday: Boolean = false,
    var isWednesday: Boolean = false,
    var isThursday: Boolean = false,
    var isFriday: Boolean = false,
    var isSaturday: Boolean = false,
    var isSunday: Boolean = false,

    var endTypes: CareRepeatEndTypes = CareRepeatEndTypes.NONE,
    var endAfterTimes: String = DEFAULT_END_AFTER_TIMES,
    var endAfterDate: Long = Calendar.getInstance().timeInMillis,
) {
    companion object {
        const val DEFAULT_INTERVAL_TIMES = "1"
        const val DEFAULT_END_AFTER_TIMES = "1"
    }
}