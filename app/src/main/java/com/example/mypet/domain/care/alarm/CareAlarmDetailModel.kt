package com.example.mypet.domain.care.alarm

import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import java.time.LocalDateTime
import java.util.Calendar

data class CareAlarmDetailModel(
    val id: Int = DEFAULT_ID,
    val nextStart: Long = Calendar.getInstance().timeInMillis,
    var hour: Int = LocalDateTime.now().hour,
    var minute: Int = LocalDateTime.now().minute,
    var description: String? = null,
    var ringtonePath: String? = null,
    var isVibration: Boolean = true,
    var isDelay: Boolean = false,
    var isActive: Boolean = false,
)