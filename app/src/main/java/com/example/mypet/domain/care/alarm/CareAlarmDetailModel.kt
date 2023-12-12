package com.example.mypet.domain.care.alarm

import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import java.time.LocalDateTime


sealed class CareAlarmDetailModel(
    open val id: Int,
    open val hour: Int,
    open val minute: Int,
)

data class CareAlarmDetailMainModel(
    override val id: Int = DEFAULT_ID,
    override var hour: Int = LocalDateTime.now().hour,
    override var minute: Int = LocalDateTime.now().minute,
    var description: String? = null,
    var ringtonePath: String? = null,
    var isVibration: Boolean = true,
    var isDelay: Boolean = false,
    var isActive: Boolean = true,
) : CareAlarmDetailModel(id, hour, minute)

data class CareAlarmDetailAddModel(
    override val id: Int = -1,
    override val hour: Int = 25,
    override val minute: Int = 0,
) : CareAlarmDetailModel(id, hour, minute)