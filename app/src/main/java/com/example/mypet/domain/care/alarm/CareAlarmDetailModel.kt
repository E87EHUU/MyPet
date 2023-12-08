package com.example.mypet.domain.care.alarm

data class CareAlarmDetailModel(
    val id: Int,
    var hour: Int,
    var minute: Int,
    var description: String? = null,
    var ringtonePath: String? = null,
    var isVibration: Boolean,
    var isDelay: Boolean,
    var isActive: Boolean = false,
)