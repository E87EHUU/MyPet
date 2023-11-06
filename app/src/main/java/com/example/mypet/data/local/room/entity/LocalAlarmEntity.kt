package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID

const val ALARM_TABLE = "alarm"

@Entity(tableName = ALARM_TABLE)
data class LocalAlarmEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = HOUR)
    val hour: Int,
    @ColumnInfo(name = MINUTE)
    val minute: Int,

    @ColumnInfo(name = REPEAT_MONDAY)
    val repeatMonday: Boolean = REPEAT_DEFAULT,
    @ColumnInfo(name = REPEAT_TUESDAY)
    val repeatTuesday: Boolean = REPEAT_DEFAULT,
    @ColumnInfo(name = REPEAT_WEDNESDAY)
    val repeatWednesday: Boolean = REPEAT_DEFAULT,
    @ColumnInfo(name = REPEAT_THURSDAY)
    val repeatThursday: Boolean = REPEAT_DEFAULT,
    @ColumnInfo(name = REPEAT_FRIDAY)
    val repeatFriday: Boolean = REPEAT_DEFAULT,
    @ColumnInfo(name = REPEAT_SATURDAY)
    val repeatSaturday: Boolean = REPEAT_DEFAULT,
    @ColumnInfo(name = REPEAT_SUNDAY)
    val repeatSunday: Boolean = REPEAT_DEFAULT,

    @ColumnInfo(name = MELODY_URI)
    val melodyURI: String?,

    @ColumnInfo(name = IS_VIBRATION)
    val isVibration: Boolean,

    @ColumnInfo(name = IS_DELAY)
    val isDelay: Boolean,

    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean = IS_ACTIVE_DEFAULT,
) {
    companion object {
        const val HOUR = "hour"
        const val MINUTE = "minute"
        const val REPEAT_MONDAY = "repeat_monday"
        const val REPEAT_TUESDAY = "repeat_tuesday"
        const val REPEAT_WEDNESDAY = "repeat_wednesday"
        const val REPEAT_THURSDAY = "repeat_thursday"
        const val REPEAT_FRIDAY = "repeat_friday"
        const val REPEAT_SATURDAY = "repeat_saturday"
        const val REPEAT_SUNDAY = "repeat_sunday"
        const val MELODY_URI = "melody_uri"
        const val IS_VIBRATION = "is_vibration"
        const val IS_DELAY = "is_delay"
        const val IS_ACTIVE = "is_active"

        const val REPEAT_DEFAULT = false
        const val IS_ACTIVE_DEFAULT = true
    }
}