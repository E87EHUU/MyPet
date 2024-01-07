package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID

const val END_TABLE = "ending"

@Entity(tableName = END_TABLE)
data class LocalEndEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = "${CARE_TABLE}_$ID")
    val careId: Int,

    @ColumnInfo(name = END_TYPE_ORDINAL)
    var typeOrdinal: Int?,
    @ColumnInfo(name = AFTER_TIMES)
    var afterTimes: Int?,
    @ColumnInfo(name = AFTER_DATE)
    var afterDate: Long?,
) {
    companion object {
        const val END_TYPE_ORDINAL = "type_ordinal"
        const val AFTER_TIMES = "after_times"
        const val AFTER_DATE = "after_date"
    }
}