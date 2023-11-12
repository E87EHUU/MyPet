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

    @ColumnInfo(name = IS_REPEAT_MONDAY)
    val isRepeatMonday: Boolean = IS_REPEAT_STATE_DEFAULT,
    @ColumnInfo(name = IS_REPEAT_TUESDAY)
    val isRepeatTuesday: Boolean = IS_REPEAT_STATE_DEFAULT,
    @ColumnInfo(name = IS_REPEAT_WEDNESDAY)
    val isRepeatWednesday: Boolean = IS_REPEAT_STATE_DEFAULT,
    @ColumnInfo(name = IS_REPEAT_THURSDAY)
    val isRepeatThursday: Boolean = IS_REPEAT_STATE_DEFAULT,
    @ColumnInfo(name = IS_REPEAT_FRIDAY)
    val isRepeatFriday: Boolean = IS_REPEAT_STATE_DEFAULT,
    @ColumnInfo(name = IS_REPEAT_SATURDAY)
    val isRepeatSaturday: Boolean = IS_REPEAT_STATE_DEFAULT,
    @ColumnInfo(name = IS_REPEAT_SUNDAY)
    val isRepeatSunday: Boolean = IS_REPEAT_STATE_DEFAULT,

    @ColumnInfo(name = RINGTONE_PATH)
    val ringtoneURI: String?,

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
        const val IS_REPEAT_MONDAY = "is_repeat_monday"
        const val IS_REPEAT_TUESDAY = "is_repeat_tuesday"
        const val IS_REPEAT_WEDNESDAY = "is_repeat_wednesday"
        const val IS_REPEAT_THURSDAY = "is_repeat_thursday"
        const val IS_REPEAT_FRIDAY = "is_repeat_friday"
        const val IS_REPEAT_SATURDAY = "is_repeat_saturday"
        const val IS_REPEAT_SUNDAY = "is_repeat_sunday"
        const val RINGTONE_PATH = "ringtone_path"
        const val IS_VIBRATION = "is_vibration"
        const val IS_DELAY = "is_delay"
        const val IS_ACTIVE = "is_active"

        const val IS_REPEAT_STATE_DEFAULT = false
        const val IS_ACTIVE_DEFAULT = true
    }
}