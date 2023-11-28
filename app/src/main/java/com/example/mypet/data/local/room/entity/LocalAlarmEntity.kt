package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID

const val ALARM_TABLE = "alarm"

@Entity(tableName = ALARM_TABLE)
data class LocalAlarmEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = DESCRIPTION)
    val description: String?,

    @ColumnInfo(name = HOUR)
    val hour: Int,
    @ColumnInfo(name = MINUTE)
    val minute: Int,
    @ColumnInfo(name = DAY)
    val day: Int? = null,
    @ColumnInfo(name = MONTH)
    val month: Int? = null,
    @ColumnInfo(name = YEAR)
    val year: Int? = null,

    @ColumnInfo(name = RINGTONE_PATH)
    val ringtonePath: String?,

    @ColumnInfo(name = IS_VIBRATION)
    val isVibration: Boolean?,
    @ColumnInfo(name = IS_DELAY)
    val isDelay: Boolean?,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean = IS_ACTIVE_DEFAULT,

    @ColumnInfo(name = REPEAT_TYPE_ORDINAL)
    val repeatTypeOrdinal: Int? = null,
    @ColumnInfo(name = REPEAT_INTERVAL)
    val repeatInterval: Int? = null,

    @ColumnInfo(name = IS_REPEAT_MONDAY)
    val isRepeatMonday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_TUESDAY)
    val isRepeatTuesday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_WEDNESDAY)
    val isRepeatWednesday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_THURSDAY)
    val isRepeatThursday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_FRIDAY)
    val isRepeatFriday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_SATURDAY)
    val isRepeatSaturday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_SUNDAY)
    val isRepeatSunday: Boolean? = null,

    @ColumnInfo(name = END_DAY)
    val endDay: Int? = null,
    @ColumnInfo(name = END_MONTH)
    val endMonth: Int? = null,
    @ColumnInfo(name = END_YEAR)
    val endYear: Int? = null,

    @ColumnInfo(name = END_COUNT)
    val endCount: Int? = null,
) {
    companion object {
        const val HOUR = "hour"
        const val MINUTE = "minute"
        const val DAY = "day"
        const val MONTH = "month"
        const val YEAR = "year"
        const val REPEAT_TYPE_ORDINAL = "repeat_type_ordinal"
        const val REPEAT_INTERVAL = "repeat_interval"
        const val IS_REPEAT_MONDAY = "is_repeat_monday"
        const val IS_REPEAT_TUESDAY = "is_repeat_tuesday"
        const val IS_REPEAT_WEDNESDAY = "is_repeat_wednesday"
        const val IS_REPEAT_THURSDAY = "is_repeat_thursday"
        const val IS_REPEAT_FRIDAY = "is_repeat_friday"
        const val IS_REPEAT_SATURDAY = "is_repeat_saturday"
        const val IS_REPEAT_SUNDAY = "is_repeat_sunday"
        const val END_DAY = "end_day"
        const val END_MONTH = "end_month"
        const val END_YEAR = "end_year"
        const val END_COUNT = "end_count"
        const val RINGTONE_PATH = "ringtone_path"
        const val IS_VIBRATION = "is_vibration"
        const val IS_DELAY = "is_delay"
        const val IS_ACTIVE = "is_active"

        const val IS_ACTIVE_DEFAULT = true
    }
}