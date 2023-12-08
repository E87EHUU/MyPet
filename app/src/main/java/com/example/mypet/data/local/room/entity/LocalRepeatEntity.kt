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

    @ColumnInfo(name = TYPE_ORDINAL)
    val typeOrdinal: Int? = null,
    @ColumnInfo(name = INTERVAL)
    val interval: Int? = null,

    @ColumnInfo(name = IS_MONDAY)
    val isMonday: Boolean? = null,
    @ColumnInfo(name = IS_TUESDAY)
    val isTuesday: Boolean? = null,
    @ColumnInfo(name = IS_WEDNESDAY)
    val isWednesday: Boolean? = null,
    @ColumnInfo(name = IS_THURSDAY)
    val isThursday: Boolean? = null,
    @ColumnInfo(name = IS_FRIDAY)
    val isFriday: Boolean? = null,
    @ColumnInfo(name = IS_SATURDAY)
    val isSaturday: Boolean? = null,
    @ColumnInfo(name = IS_SUNDAY)
    val isSunday: Boolean? = null,

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
        const val TYPE_ORDINAL = "type_ordinal"
        const val INTERVAL = "interval"

        const val IS_MONDAY = "is_monday"
        const val IS_TUESDAY = "is_tuesday"
        const val IS_WEDNESDAY = "is_wednesday"
        const val IS_THURSDAY = "is_thursday"
        const val IS_FRIDAY = "is_friday"
        const val IS_SATURDAY = "is_saturday"
        const val IS_SUNDAY = "is_sunday"

        const val END_DAY = "end_day"
        const val END_MONTH = "end_month"
        const val END_YEAR = "end_year"
        const val END_COUNT = "end_count"
    }
}