package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
import com.example.mypet.data.local.room.LocalDatabase.Companion.HOUR
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.MINUTE

const val ALARM_TABLE = "alarm"

@Entity(tableName = ALARM_TABLE)
data class LocalAlarmEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = "${CARE_TABLE}_$ID")
    val careId: Int,

    @ColumnInfo(name = NEXT_START)
    val nextStart: Long?,
    @ColumnInfo(name = INTERVAL_START)
    val intervalStart: Long?,

    @ColumnInfo(name = DESCRIPTION)
    val description: String?,

    @ColumnInfo(name = HOUR)
    val hour: Int,
    @ColumnInfo(name = MINUTE)
    val minute: Int,

    @ColumnInfo(name = RINGTONE_PATH)
    val ringtonePath: String?,

    @ColumnInfo(name = IS_VIBRATION)
    val isVibration: Boolean?,
    @ColumnInfo(name = IS_DELAY)
    val isDelay: Boolean?,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean = IS_ACTIVE_DEFAULT,
) {
    companion object {
        const val NEXT_START = "next_start"
        const val INTERVAL_START = "interval_start"

        const val RINGTONE_PATH = "ringtone_path"

        const val IS_VIBRATION = "is_vibration"
        const val IS_DELAY = "is_delay"
        const val IS_ACTIVE = "is_active"

        const val IS_ACTIVE_DEFAULT = true
    }
}