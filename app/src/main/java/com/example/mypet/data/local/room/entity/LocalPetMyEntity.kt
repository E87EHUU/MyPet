package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME

const val PET_MY_TABLE = "pet_my"

@Entity(tableName = PET_MY_TABLE)
data class LocalPetMyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,
    @ColumnInfo(name = "${PET_KIND_TABLE}_$ID")
    val kindId: Int,
    @ColumnInfo(name = "${PET_BREED_TABLE}_$ID")
    val breedId: Int?,

    @ColumnInfo(name = AVATAR_PATH)
    val avatarPath: String,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = AGE)
    val age: Int?,
    @ColumnInfo(name = WEIGHT)
    val weight: Int?,
    @ColumnInfo(name = SEX)
    val sex: Int?,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean,
) {
    companion object {
        const val AVATAR_PATH = "avatar_path"
        const val AGE = "age"
        const val WEIGHT = "weight"
        const val SEX = "sex"
        const val IS_ACTIVE = "is_active"
    }
}