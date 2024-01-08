package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
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

    @ColumnInfo(name = TITLE)
    val title: String?,

    @ColumnInfo(name = DOSE)
    val dose: String?,
) {
    companion object {
        const val CARE_TYPE_ORDINAL = "care_type_ordinal"

        const val TITLE = "title"

        const val DOSE = "dose"
    }
}