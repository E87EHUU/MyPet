package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.HOUR
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.MINUTE

const val START_TABLE = "start"

@Entity(tableName = START_TABLE)
data class LocalStartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = "${CARE_TABLE}_$ID")
    val careId: Int,

    @ColumnInfo(name = TIME_IN_MILLIS)
    val timeInMillis: Long? = null,
    // FIXME час и минуты стартовые не используются
    @ColumnInfo(name = HOUR)
    val hour: Int? = null,
    @ColumnInfo(name = MINUTE)
    val minute: Int? = null,
) {
    companion object {
        const val TIME_IN_MILLIS = "time_in_millis"
    }
}