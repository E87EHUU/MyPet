package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME

const val PET_TABLE = "pet"

@Entity(tableName = PET_TABLE)
data class LocalPetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,
    @ColumnInfo(name = AVATAR_PATH)
    val avatarPath: String?,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = AGE)
    val dateOfBirthTimeMillis: Long?,
    @ColumnInfo(name = WEIGHT)
    val weight: Int?,
    @ColumnInfo(name = SEX)
    val sex: Int?,
    @ColumnInfo(name = KIND_ORDINAL)
    val kindOrdinal: Int,
    @ColumnInfo(name = BREED_ORDINAL)
    val breedOrdinal: Int?,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean,
) {
    companion object {
        const val AVATAR_PATH = "avatar_path"
        const val AGE = "age"
        const val WEIGHT = "weight"
        const val SEX = "sex"
        const val IS_ACTIVE = "is_active"
        const val KIND_ORDINAL = "kind_ordinal"
        const val BREED_ORDINAL = "breed_ordinal"
    }
}
