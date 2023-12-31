package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID

const val REPEAT_TABLE = "repeat"

@Entity(tableName = REPEAT_TABLE)
data class LocalRepeatEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = "${CARE_TABLE}_$ID")
    val careId: Int,

    @ColumnInfo(name = INTERVAL_ORDINAL)
    val intervalOrdinal: Int? = null,
    @ColumnInfo(name = INTERVAL_TIMES)
    val intervalTimes: Int? = null,

    @ColumnInfo(name = IS_MONDAY)
    val isMonday: Boolean = false,
    @ColumnInfo(name = IS_TUESDAY)
    val isTuesday: Boolean = false,
    @ColumnInfo(name = IS_WEDNESDAY)
    val isWednesday: Boolean = false,
    @ColumnInfo(name = IS_THURSDAY)
    val isThursday: Boolean = false,
    @ColumnInfo(name = IS_FRIDAY)
    val isFriday: Boolean = false,
    @ColumnInfo(name = IS_SATURDAY)
    val isSaturday: Boolean = false,
    @ColumnInfo(name = IS_SUNDAY)
    val isSunday: Boolean = false,
) {
    companion object {
        const val INTERVAL_TIMES = "interval_times"
        const val INTERVAL_ORDINAL = "interval_ordinal"

        const val IS_MONDAY = "is_monday"
        const val IS_TUESDAY = "is_tuesday"
        const val IS_WEDNESDAY = "is_wednesday"
        const val IS_THURSDAY = "is_thursday"
        const val IS_FRIDAY = "is_friday"
        const val IS_SATURDAY = "is_saturday"
        const val IS_SUNDAY = "is_sunday"
    }
}