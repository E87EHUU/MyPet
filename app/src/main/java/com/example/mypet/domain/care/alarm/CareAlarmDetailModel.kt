package com.example.mypet.domain.care.alarm

import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import java.time.LocalDateTime

data class CareAlarmDetailModel(
    val id: Int = DEFAULT_ID,
    var hour: Int = LocalDateTime.now().hour,
    var minute: Int = LocalDateTime.now().minute,
    var ringtonePath: String? = null,
    var isVibration: Boolean = true,
    var isDelay: Boolean = false,
    var isActive: Boolean = true,
)