package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID

const val CARE_TABLE = "care"

@Entity(tableName = CARE_TABLE)
data class LocalCareEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = "${PET_TABLE}_$ID")
    val petId: Int,
    @ColumnInfo(name = CARE_TYPE_ORDINAL)
    val careTypeOrdinal: Int,

    @ColumnInfo(name = DESCRIPTION)
    val description: String?,

    @ColumnInfo(name = PROGRESS)
    val progress: Int? = null,

    @ColumnInfo(name = START_DAY)
    val startDay: Int? = null,
    @ColumnInfo(name = START_MONTH)
    val startMonth: Int? = null,
    @ColumnInfo(name = START_YEAR)
    val startYear: Int? = null,

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
        const val CARE_TYPE_ORDINAL = "care_type_ordinal"

        const val PROGRESS = "progress"

        const val START_DAY = "start_day"
        const val START_MONTH = "start_month"
        const val START_YEAR = "start_year"

        const val END_DAY = "end_day"
        const val END_MONTH = "end_month"
        const val END_YEAR = "end_year"
        const val END_COUNT = "end_count"
    }
}